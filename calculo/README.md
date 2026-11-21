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

## Actualización automática

Cada cambio que llega a `main` se publica en Pages y **los teléfonos lo reciben solos**,
sin reinstalar nada:

- **Web instalada**: al abrir o volver a la app busca la versión publicada; si cambió, se
  recarga sola (si hay una sesión en curso, espera a que termine). El progreso no se toca.
- **APK**: abre la versión publicada en Pages y guarda una copia de cada archivo. Sin
  internet usa la última copia descargada; en el primer arranque sin red, la que trae el
  APK. Siempre se sirve desde la misma dirección, así que el progreso es uno solo.

Regla: **sube `APP_VERSION`** en `index.html` (y `CACHE` en `sw.js`) en cada cambio; así
es como los teléfonos notan que hay versión nueva. Reinstalar el APK solo hace falta si
cambia algo de Android (`android/`: icono, nombre, permisos).

## APK

`android/` es una app Android mínima (WebView). El workflow `.github/workflows/cuanti-apk.yml`
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
