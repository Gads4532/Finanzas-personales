# Finanzas Personales — banco de ideas

Referencia: **Rial – Finanzas Personales** (App Store, pensada para Venezuela). Rial
pide cuenta y tiene suscripción Plus; esta versión es gratis, sin registro, funciona sin
internet y sin Claude. Mismo sistema que el Habit Tracker: un solo `index.html`,
`sw.js`, `manifest.webmanifest`, iconos, rama `gh-pages` para publicar.

## Requisito base

- **Funciona sola**: PWA instalable, datos en `localStorage`, copia `.json` y `.csv`.
  Dentro de Claude, además, nube por persona (`claude.use('db')`). ✅
- **Multimoneda**: cada cuenta es en dólares, bolívares o USDT. Todo se compara en
  dólares; cada movimiento guarda la tasa del día en que se anotó. ✅

## Lo que tiene Rial y cómo está aquí

| Rial | Aquí | Estado |
|---|---|---|
| Saldo consolidado USD + Bs + USDT | «Saldo total» arriba; tocarlo cambia entre $ y Bs | ✅ |
| Widget con la tasa BCV | Tasa arriba a la derecha con la hora de la última actualización | ✅ |
| Tasas en tiempo real (BCV, USDT, euro) y calculadora | Pantalla «Tasas»: dólar BCV, USDT Binance P2P, euro BCV y paralelo, con ▲▼ frente al valor anterior. Se refrescan solas cada minuto mientras la app está a la vista, al volver a ella y al recuperar la red; sin intervención. Fuentes públicas con CORS abierto: ve.dolarapi.com (USD/EUR) y criptoya.com (USDT). Calculadora: escribes un monto en $, USDT, € o Bs y ves los otros tres; «Calcular bolívares con» BCV, USDT o paralelo. Por si no hay red: «Escribir tasas a mano» | ✅ |
| Registro por voz con IA («Gasté 5$ en café») | Barra «Anotar rápido» + micrófono del navegador; un analizador local saca monto, moneda, tipo, cuenta, categoría y fecha. Sin IA, sin Claude | ✅ |
| — | Fechas relativas («ayer», «antier», «hace 3 días», «el lunes», «la semana pasada», «mañana») y concretas («el 15», «15/10», «3/9/2025», «5 de octubre») | ✅ |
| Gráficos por categoría e historial mensual | Barras finas por categoría en Presupuesto y «gastaste X% más/menos que el mes pasado». Se quitaron la dona y las barras de 6 meses: menos, pero mejor | ✅ |
| Historial | Tocar el nombre del mes en Movimientos abre el historial: un renglón por mes con ingresos, gastos y balance, y el total de todo. En Presupuesto, cada categoría enseña sus últimos 6 meses | ✅ |
| Pestaña Análisis | Mes · 3 meses · Año: ahorro y tasa de ahorro, gasto por día, comparación con el periodo anterior y qué categoría subió más, día más caro, gastos por categoría con barras, últimos 6 meses y mayores gastos | ✅ |
| Categorías propias | Se crean desde el propio formulario de movimiento («+ Nueva» entre las categorías, y vuelve con ella elegida) o desde Ajustes → Categorías, donde también se editan | ✅ |
| Calendario de movimientos, totales por día, filtros, búsqueda | Todos · Gastos · Ingresos; calendario y buscador escondidos tras un icono; el calendario tiñe cada día según lo gastado | ✅ |
| Presupuestos, gasto libre, compromisos del mes | Plan del mes: **ingresos esperados** por categoría (recibido de esperado, barra verde) y **límite de gasto** por categoría (ámbar al 80%, rojo al pasarse). «Por asignar» = esperado − previsto (dale un destino a cada dólar). «Libre para gastar» = ingresos − gastos − por pagar, también por día | ✅ |
| Pagos programados (semanal, quincenal…) | Semanal, quincenal, mensual, anual; botón «Pagar» que anota el gasto y pasa al siguiente | ✅ |
| Metas y ahorros con aportes y retiros desde cualquier cuenta | Metas con anillo de progreso, cuota mensual hasta la fecha y celebración al cumplirla | ✅ |
| Cuenta y nube propia | Igual que el Habit Tracker 2.x: «Crear cuenta o iniciar sesión» en Ajustes, con correo y contraseña (Supabase). Los datos van a la tabla `finanzas` del mismo proyecto (una fila por persona, protegida por RLS). La cuenta manda siempre, sin preguntar. Sin conexión sigue guardando en el teléfono y sube al volver la red. Con cuenta, la copia local se oculta. SQL en `supabase.sql` | ✅ |
| Actualizaciones | Versión al pie de Inicio, «Buscar actualización» y aviso «Hay una versión nueva lista» con botón Actualizar. Los datos viven aparte y nunca se tocan al actualizar | ✅ |
| Registro con nombre | Bienvenida la primera vez con nombre y apellido; Inicio saluda «Hola, Gerardo». Se cambian en Ajustes (al final de Inicio), junto a la tasa y las copias | ✅ |
| Transferencias entre cuentas | Con conversión automática si cambian de moneda (editable) | ✅ |
| Cobrar / Pagar (deudas) | — | ⏳ |
| Lista de compras | — | ⏳ |
| Adjuntar comprobantes | — | ⏳ |
| Perfil financiero con IA | Dentro de Claude se podría pedir con `claude.use` | ⏳ |
| Histórico del dólar con gráfico | Guardar la tasa de cada día y dibujarla | ⏳ |

## Instalación en el teléfono

- Chrome → ⋮ → «Instalar aplicación». En Samsung Galaxy, borrar el icono de la pantalla de inicio no desinstala la app: queda en Ajustes → Aplicaciones.
- Si Chrome dice que ya está instalada y no ofrece reinstalar: borrar la página `gads4532.github.io` del **Historial** de Chrome lo arregla (probado). También sirve Configuración → Configuración de sitios → Datos almacenados → borrar.

## Diseño

**Muy minimalista**: cada pantalla enseña tres cifras y una lista; lo demás aparece al tocar.
Sin tarjetas ni gráficos de adorno; las categorías sin plan se resumen en una línea de enlaces.
El botón + es blanco y negro, sin sombra.

Idéntico al Habit Tracker: líneas finas, sin tarjetas, símbolo y color por cuenta,
categoría y meta, diálogos que suben con resorte, barra inferior, modo oscuro,
animaciones con resortes `linear()` que se apagan con «reducir movimiento». El color
de la app es verde (`--accent: #12a150`) en lugar del naranja del fuego.

## Finanzas Plus (modelo de ingresos, como Rial Plus)

- **Gratis**: cuentas, movimientos, tasas, presupuesto, 1 meta, Análisis del mes, historial de 3 meses.
- **Plus** ($ 2,99/mes o $ 19,99 de por vida): Análisis de 3 meses y año, historial completo, categorías propias, metas ilimitadas, exportar a Excel. Sin anuncios nunca.
- Bloqueo suave: la función se ve; al tocarla sale la ficha de Plus con lo que incluye y el precio.
- Plus vive en la tabla `plus` de Supabase (`plus.sql`), ligada a la cuenta; la app solo la lee. Se activa a mano desde el SQL Editor (hay plantillas en `plus.sql`) o, más adelante, desde el webhook de la pasarela de pago. Sin cuenta no hay Plus: eso empuja al registro.
- Cobro: pendiente de elegir. Desde Venezuela no hay cuenta de comerciante de Google Play ni Stripe; opciones realistas: Lemon Squeezy/Paddle (pagan a PayPal/Payoneer) para la web, y Binance Pay / Pago Móvil con activación manual para lo local.
- `privacidad.html`: política de privacidad y términos, enlazada desde Ajustes. Obligatoria para la tienda.
- Play Store: la PWA se empaqueta como Trusted Web Activity con Bubblewrap (`twa-manifest.json`) y se verifica el dominio con `.well-known/assetlinks.json` (falta la huella SHA-256 del certificado de firma, que sale al generar el paquete). Requiere cuenta de desarrollador (25 USD) y, en cuentas personales nuevas, 14 días de prueba cerrada con 12 testers.

## Regla para todas las apps

- **La cuenta manda, nunca se pregunta**: jamás un diálogo de «¿qué datos conservar?». Al abrir, si la nube tiene datos, se adoptan en silencio; lo único que sube del teléfono es lo que aún no se había subido (cambios sin red), y la app insiste cada 15 s hasta subirlo. El teléfono recuerda qué versión de la nube ya tiene (`finanzas-seen`) para no volver a compararla. Igual que el Habit Tracker 2.6.x.

- **Sin borde blanco**: `html` y `body` llevan el fondo del tema (`var(--bg)`) a todo el alto, `<meta name="color-scheme" content="light dark">`, y en el manifiesto `background_color` y `theme_color` son `#0e0e0e`. Así en modo oscuro no aparece ninguna franja blanca arriba, abajo ni al rebotar. Igual que el Habit Tracker desde la 2.3.1/2.4.1; se aplica a cualquier app nueva.
