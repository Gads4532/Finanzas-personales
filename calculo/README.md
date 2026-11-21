# Cuanti

Entrenador de cálculo mental para desarrollar la capacidad cuantitativa. Un solo archivo
(`index.html`), sin dependencias, funciona sin conexión y guarda el progreso en el teléfono.

- **Practicar**: 16 temas en 4 categorías (Aritmética · Números · Proporciones y dinero ·
  Álgebra y datos), más «Mixto esencial» y «Desafío total». Los ejercicios se generan al
  azar, así que nunca se acaban.
- **Dificultad**: Adaptativa (cada tema sube cuando aciertas rápido y baja cuando fallas),
  Fácil (niveles 1–3), Media (4–6), Difícil (7–8) y Experto (9–10).
- **Formatos**: Sprint de 60 s, 10 preguntas o Libre. Teclado numérico en pantalla y
  teclado físico (Enter envía, Espacio salta, Esc sale).
- **Lecciones**: 18 técnicas (× 11, cuadrados que terminan en 5, porcentajes por partes,
  regla de tres, ecuaciones, probabilidad, interés compuesto…). Cada una tiene idea,
  pasos, ejemplo resuelto, «Pruébalo» y una práctica guiada de 10 ejercicios; con 8 o más
  queda completada.
- **Progreso**: meta diaria, racha, aciertos de 14 días, nivel y precisión por tema,
  mejores marcas y sesiones recientes.

Web: https://gads4532.github.io/Finanzas-personales/calculo/ (se instala desde Chrome).

## APK

`android/` es una app Android mínima (WebView) que lleva `index.html` **dentro** del APK:
abre sin internet y no depende de la web. El workflow `.github/workflows/cuanti-apk.yml`
la compila en cada cambio de `calculo/`:

1. GitHub → **Actions** → «APK de Cuanti» → la última ejecución → **Artifacts** →
   `Cuanti-apk` (un .zip con el APK). En el teléfono, ábrelo y permite «instalar apps de
   origen desconocido».
2. Sin secretos, el APK es **de prueba** (firma de depuración que cambia en cada
   ejecución): para actualizar hay que desinstalar antes y se pierde el progreso.
3. Para un APK con firma fija (que actualiza encima, sin perder datos), crea la clave
   **en tu portátil**, nunca en el repo:

   ```bash
   keytool -genkeypair -v -keystore cuanti.keystore -alias cuanti -keyalg RSA -keysize 2048 -validity 10000
   base64 -w0 cuanti.keystore
   ```

   y añade en Settings → Secrets and variables → Actions: `CUANTI_KEYSTORE_B64` (la
   salida de base64), `CUANTI_KEYSTORE_PASSWORD`, `CUANTI_KEY_ALIAS` (`cuanti`) y
   `CUANTI_KEY_PASSWORD`. Guarda copias de la clave como dice `../ANDROID.md` §2.
   A partir de ahí, cada cambio en `main` publica el APK firmado en la Release
   `cuanti-latest`.

`applicationId` = `io.github.gads4532.cuanti`, fijo para siempre. Sube `versionCode` en
`android/app/build.gradle` cada vez que repartas un APK nuevo.
