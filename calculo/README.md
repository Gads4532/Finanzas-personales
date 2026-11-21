# Cuanti

Entrenador de razonamiento cuantitativo. Un solo archivo (`index.html`), sin dependencias,
funciona sin conexión y guarda el progreso en el teléfono.

**Pista principal: Razonar.** Situaciones reales con una conclusión. En cada una se responde en
dos capas (¿se sostiene? y ¿qué regla está en juego?) y se declara la confianza (50/65/80/95%).
La regla vale 75 puntos y la conclusión 25. La confianza se puntúa con Brier y alimenta una
curva propia de calibración. Cada corrección nombra la regla, explica en tres líneas por qué
falla y da el dato concreto que habría vuelto válida la conclusión.

- 16 reglas en 4 áreas (evidencia, causalidad, probabilidad, magnitudes), elegidas porque
  describen cómo funciona el mundo, reaparecen en dominios sin relación y un universitario
  capaz las pasa por alto.
- 80 situaciones: cada regla en negocios, salud, política, dinero y relaciones, con una
  variante válida y otra no válida.
- Variación de superficie forzada: se registra cada combinación regla × contexto y se
  priorizan las nunca vistas. Métrica aparte: acierto de la regla en contexto nuevo.
- Sesión: calentamiento de 90 s como máximo + 8 situaciones. El calentamiento tiene su propia
  métrica («automaticidad»), que nunca se mezcla con la de razonamiento.

**Pista de cálculo.** Todo lo que la app tenía: 15 temas, cinco dificultades, Sprint 60 s,
10 preguntas, Libre, Mixto esencial y Desafío total (solo de cálculo), meta diaria, racha,
mejores marcas y 13 lecciones. Sus métricas no tocan las de razonamiento.

**Más.** Trucos (× 11, × 99, cuadrados que terminan en 5, cuadrados hasta 130, cubos…) y
Patrones (series de examen), sin nivel adaptativo y sin métrica; y «Tus datos y archivo».

La versión 2 migra los datos anteriores sin borrar nada: guarda una copia íntegra
(`cuanti.backup.v1`) y archiva lo que ya no encaja (estadísticas de Series, la marca del
Desafío total que incluía series).

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
