package io.github.gads4532.cuanti;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Cuanti se actualiza sola: abre la versión publicada en GitHub Pages y guarda una copia de
 * cada archivo. Sin internet usa la última copia descargada y, si aún no hay ninguna, la que
 * viene dentro del APK. Todo se sirve siempre desde la misma dirección, así que el progreso
 * (localStorage) es uno solo, con o sin red.
 */
public class MainActivity extends Activity {
    static final String BASE = "https://gads4532.github.io/Finanzas-personales/calculo/";
    static final List<String> FILES = Arrays.asList("index.html", "icon.svg", "manifest.webmanifest", "icon-512.png");
    private WebView web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setTextZoom(100);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);
        s.setUserAgentString(s.getUserAgentString() + " CuantiApp");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest req) {
                return serve(req);
            }
        });
        web.setWebChromeClient(new WebChromeClient()); // muestra los diálogos de confirmación
        web.setBackgroundColor(getResources().getColor(R.color.bg, getTheme()));
        setContentView(web);
        if (state != null) web.restoreState(state);
        else web.loadUrl(BASE + "index.html");
    }

    /** Red primero (con tope de tiempo), luego copia guardada, luego copia del APK. */
    private WebResourceResponse serve(WebResourceRequest req) {
        Uri u = req.getUrl();
        String url = u.toString();
        if (!"GET".equals(req.getMethod()) || !url.startsWith(BASE)) return null;
        String path = u.getPath() == null ? "" : u.getPath();
        String name = path.substring(path.lastIndexOf('/') + 1);
        if (name.isEmpty()) name = "index.html";
        if (!FILES.contains(name)) return null;

        File saved = new File(new File(getFilesDir(), "web"), name);
        byte[] data = null;
        try {
            // El parámetro evita la caché del CDN: los cambios llegan en cuanto se publican.
            HttpURLConnection c = (HttpURLConnection) new URL(BASE + name + "?v=" + System.currentTimeMillis()).openConnection();
            c.setConnectTimeout(4000);
            c.setReadTimeout(6000);
            c.setUseCaches(false);
            if (c.getResponseCode() == 200) {
                data = readAll(c.getInputStream());
                saved.getParentFile().mkdirs();
                try (FileOutputStream out = new FileOutputStream(saved)) { out.write(data); }
            }
            c.disconnect();
        } catch (IOException ignored) { }
        if (data == null && saved.exists()) {
            try (InputStream in = new FileInputStream(saved)) { data = readAll(in); } catch (IOException ignored) { }
        }
        if (data == null) {
            try (InputStream in = getAssets().open(name)) { data = readAll(in); } catch (IOException e) { return null; }
        }
        WebResourceResponse r = new WebResourceResponse(mime(name), name.endsWith(".png") ? null : "utf-8", new ByteArrayInputStream(data));
        return r;
    }

    private static String mime(String n) {
        if (n.endsWith(".html")) return "text/html";
        if (n.endsWith(".svg")) return "image/svg+xml";
        if (n.endsWith(".png")) return "image/png";
        return "application/manifest+json";
    }

    private static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[16384];
        for (int n; (n = in.read(buf)) > 0; ) out.write(buf, 0, n);
        return out.toByteArray();
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    /** «Atrás» cierra la sesión, lección o pestaña abierta; en la portada sale de la app. */
    @Override
    @SuppressWarnings("deprecation")
    public void onBackPressed() {
        web.evaluateJavascript("window.cuantiBack ? cuantiBack() : false", v -> {
            if (!"true".equals(v)) MainActivity.super.onBackPressed();
        });
    }
}
