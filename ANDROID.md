# Android y Play Store — reglas que no se cambian

La app es una PWA. En Android se empaqueta como **Trusted Web Activity** (una app mínima
que abre https://gads4532.github.io/Finanzas-personales/ a pantalla completa). La web
sigue actualizándose sola; el paquete Android solo cambia cuando cambia algo del
envoltorio (icono, nombre, permisos).

## 1. Identidad de la app: fija desde el primer APK

| Qué | Valor | Regla |
|---|---|---|
| `applicationId` (package name) | `io.github.gads4532.finanzas` | **No se cambia nunca.** Cambiarlo = app distinta; nadie podría actualizar. |
| Nombre visible | Finanzas Personales | Se puede cambiar. |
| `versionCode` | entero que solo sube (1, 2, 3…) | Cada paquete que se publique, dentro o fuera de Play, sube el número. |
| `versionName` | la versión de la web (`APP_VERSION`) | Informativo. |

El `applicationId` está en `twa-manifest.json`. Fuera de Play y dentro de Play se usa **el mismo**.

## 2. La clave de firma: el activo más valioso del proyecto

Android solo acepta una actualización si está firmada con la **misma clave** que la versión
instalada. Si un APK público se firma con una clave y la versión de Play con otra, quien
instaló el primero no puede actualizar: tiene que desinstalar (perdiendo lo que tenía en el
teléfono sin cuenta) y volver a instalar. Y si la clave se pierde, no se puede publicar
ninguna actualización de esa app, nunca.

Por eso, el orden es este y **se hace antes del primer APK público**:

1. **Play App Signing desde el principio.** Al crear la app en Play Console, aceptar
   «Play App Signing»: Google guarda la clave con la que firma lo que llega a los teléfonos
   (*app signing key*) y tú solo manejas una **clave de carga** (*upload key*). Si la clave
   de carga se pierde o se filtra, se puede pedir a Google que la cambie; la de firma real
   nunca sale de Google. Esto elimina el escenario «perdí la clave, perdí la app».
2. **Un solo APK para todos los canales.** Lo que se reparta fuera de Play (GitHub Releases,
   WhatsApp, web) tiene que ser el APK firmado por Google, no uno firmado a mano. Se obtiene
   en Play Console → Explorador de app bundles → la versión → **Descargar APK universal
   firmado**. Así la firma es idéntica dentro y fuera de Play y todo el mundo actualiza.
   **Nunca** publicar un APK firmado con la clave de carga ni con una clave de depuración.
3. **Crear la clave de carga en tu portátil**, nunca en un servidor ajeno ni en este repo:

   ```bash
   mkdir -p ~/finanzas-android && cd ~/finanzas-android
   keytool -genkeypair -v -keystore upload.keystore -alias finanzas \
     -keyalg RSA -keysize 2048 -validity 10000
   ```
   Pide dos contraseñas (almacén y clave): guárdalas en el gestor de contraseñas.
4. **Tres copias del `upload.keystore` y de sus contraseñas**: gestor de contraseñas (como
   adjunto), un pendrive guardado en casa y una nube cifrada (no la misma cuenta de Google de
   la Play Console). Comprobar una vez al año que las copias abren.
5. **Jamás en Git.** El `.gitignore` de este repo bloquea `*.keystore`, `*.jks`, `*.p12` y
   `*.pem`. Si alguna vez entra uno por error, se considera comprometido: se rota la clave de
   carga en Play Console.

## 3. Verificación de desarrolladores (Android, también fuera de Play)

Google exige verificar la identidad del desarrollador para que una app se instale en
Android, **aunque se distribuya fuera de Play**. Ya se aplica en Brasil, Indonesia, Singapur
y Tailandia desde el 30 de septiembre de 2026; el despliegue global está previsto para 2027.
Venezuela aún no está incluida, así que hoy un APK instala sin más, pero distribuir solo
por fuera tiene fecha de caducidad. El componente «Android Developer Verifier» ya llega a
los teléfonos por Google System Updates.

Qué hacer, en orden:

1. **Registrarse como desarrollador** en Play Console con la cuenta personal de Gerardo
   (25 USD, verificación de identidad con documento). Con cuenta personal el nombre de
   desarrollador que se muestra lo eliges tú; con cuenta de organización se muestra el
   nombre legal de la empresa y hace falta D-U-N-S. **No meter a la organización** mientras
   el proyecto sea personal.
2. **Mirar primero la «cuenta de distribución limitada»** que Google ofrece a estudiantes y
   aficionados (sin costo, con tope de instalaciones). Sirve para registrar la app y cumplir
   la verificación sin cuenta completa; si el proyecto crece, se pasa a la cuenta normal.
3. **Registrar el package name** `io.github.gads4532.finanzas` en Play Console aunque al
   principio solo se distribuya fuera de Play: desde marzo de 2026 Play Console permite
   registrar apps que se reparten por fuera, y con eso la app queda asociada a un
   desarrollador verificado y seguirá instalándose cuando la verificación llegue a Venezuela.
4. Comprobar en https://developer.android.com/developer-verification el calendario antes de
   cada lanzamiento grande: las fechas cambian.

## 4. Verificación del dominio (`assetlinks.json`)

Para que la TWA abra sin barra de navegador, `.well-known/assetlinks.json` tiene que listar
la huella SHA-256 **del certificado que firma lo que se instala**. Con Play App Signing eso
son dos huellas, y las dos van en el archivo:

- la de la **clave de firma de Google**: Play Console → Configuración → Integridad de la app
  → «Certificado de clave de firma de apps» → huella SHA-256;
- la de tu **clave de carga**, para probar paquetes locales:
  `keytool -list -v -keystore ~/finanzas-android/upload.keystore -alias finanzas`.

El archivo del repo tiene los dos huecos marcados con `REEMPLAZAR_`. GitHub Pages lo sirve
en https://gads4532.github.io/Finanzas-personales/.well-known/assetlinks.json.

## 5. Cómo generar el paquete (cuando toque)

```bash
npm i -g @bubblewrap/cli
cd Finanzas-personales
bubblewrap init --manifest https://gads4532.github.io/Finanzas-personales/manifest.webmanifest   # ya está en twa-manifest.json
bubblewrap build        # pide la ruta y contraseñas de ~/finanzas-android/upload.keystore
```

Sale `app-release-bundle.aab` (lo que se sube a Play) y `app-release-signed.apk` (solo
para probar en tu teléfono; **no** se reparte). Cada build sube `appVersionCode` en
`twa-manifest.json`.

## 6. Lista de comprobación antes del primer paquete público

- [ ] `applicationId` decidido y escrito en `twa-manifest.json` (hecho: `io.github.gads4532.finanzas`).
- [ ] Clave de carga creada en el portátil y con tres copias.
- [ ] App creada en Play Console con Play App Signing activado.
- [ ] Huellas SHA-256 (Google y carga) puestas en `.well-known/assetlinks.json` y publicadas.
- [ ] `privacidad.html` enlazada en la ficha de Play como política de privacidad.
- [ ] Prueba cerrada: 12 testers reales durante 14 días (ver IDEAS.md).
- [ ] Todo APK que se reparta fuera de Play es el **universal firmado descargado de Play Console**.
