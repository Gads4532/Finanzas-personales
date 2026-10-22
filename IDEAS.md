# Finanzas en Claro — banco de ideas

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
| Widget con la tasa BCV | Tasa BCV / paralelo arriba a la derecha, sola una vez al día; calculadora $ ⇄ Bs | ✅ |
| Registro por voz con IA («Gasté 5$ en café») | Barra «Anotar rápido» + micrófono del navegador; un analizador local saca monto, moneda, tipo, cuenta, categoría y fecha. Sin IA, sin Claude | ✅ |
| — | Fechas relativas («ayer», «antier», «hace 3 días», «el lunes», «la semana pasada», «mañana») y concretas («el 15», «15/10», «3/9/2025», «5 de octubre») | ✅ |
| Gráficos por categoría e historial mensual | Barras finas por categoría en Presupuesto y «gastaste X% más/menos que el mes pasado». Se quitaron la dona y las barras de 6 meses: menos, pero mejor | ✅ |
| Calendario de movimientos, totales por día, filtros, búsqueda | Todos · Gastos · Ingresos; calendario y buscador escondidos tras un icono; el calendario tiñe cada día según lo gastado | ✅ |
| Presupuestos, gasto libre, compromisos del mes | Plan del mes: **ingresos esperados** por categoría (recibido de esperado, barra verde) y **límite de gasto** por categoría (ámbar al 80%, rojo al pasarse). «Por asignar» = esperado − previsto (dale un destino a cada dólar). «Libre para gastar» = ingresos − gastos − por pagar, también por día | ✅ |
| Pagos programados (semanal, quincenal…) | Semanal, quincenal, mensual, anual; botón «Pagar» que anota el gasto y pasa al siguiente | ✅ |
| Metas y ahorros con aportes y retiros desde cualquier cuenta | Metas con anillo de progreso, cuota mensual hasta la fecha y celebración al cumplirla | ✅ |
| Cuenta y nube propia | Igual que el Habit Tracker 2.x: «Crear cuenta o iniciar sesión» en Ajustes, con correo y contraseña (Supabase). Los datos van a la tabla `finanzas` del mismo proyecto (una fila por persona, protegida por RLS). Si hay datos en los dos sitios, la app pregunta cuáles conservar. Sin conexión sigue guardando en el teléfono y sube al volver la red. Con cuenta, la copia local se oculta. SQL en `supabase.sql` | ✅ |
| Actualizaciones | Versión al pie de Inicio, «Buscar actualización» y aviso «Hay una versión nueva lista» con botón Actualizar. Los datos viven aparte y nunca se tocan al actualizar | ✅ |
| Registro con nombre | Bienvenida la primera vez con nombre y apellido; Inicio saluda «Hola, Gerardo». Se cambian en Ajustes (al final de Inicio), junto a la tasa y las copias | ✅ |
| Transferencias entre cuentas | Con conversión automática si cambian de moneda (editable) | ✅ |
| Cobrar / Pagar (deudas) | — | ⏳ |
| Lista de compras | — | ⏳ |
| Adjuntar comprobantes | — | ⏳ |
| Perfil financiero con IA | Dentro de Claude se podría pedir con `claude.use` | ⏳ |
| Histórico del dólar con gráfico | Guardar la tasa de cada día y dibujarla | ⏳ |

## Diseño

**Muy minimalista**: cada pantalla enseña tres cifras y una lista; lo demás aparece al tocar.
Sin tarjetas ni gráficos de adorno; las categorías sin plan se resumen en una línea de enlaces.
El botón + es blanco y negro, sin sombra.

Idéntico al Habit Tracker: líneas finas, sin tarjetas, símbolo y color por cuenta,
categoría y meta, diálogos que suben con resorte, barra inferior, modo oscuro,
animaciones con resortes `linear()` que se apagan con «reducir movimiento». El color
de la app es verde (`--accent: #12a150`) en lugar del naranja del fuego.
