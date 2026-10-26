# Billuyo

Billuyo («mis billetes y yo», suena como el criollo *biyuyo*): finanzas personales para quien maneja dólares, bolívares y USDT a la vez, inspirada en
[Rial](https://apps.apple.com/us/app/rial-finanzas-personales/id6755372307): saldo total
consolidado, tasa BCV del día, cuentas, gastos por categoría, presupuesto, pagos
programados y metas de ahorro. Un solo archivo, sin dependencias.

Se instala desde Chrome («Instalar aplicación») y funciona sin conexión y sin Claude.
Los datos se guardan en el teléfono; «Guardar copia» exporta todo a un archivo .json y
«Exportar a Excel» saca los movimientos en .csv. Las tasas (dólar BCV, USDT Binance, euro BCV y paralelo) se actualizan solas cada
minuto desde ve.dolarapi.com y criptoya.com; sin internet se escriben a mano.

Con una cuenta (correo y contraseña) los datos se sincronizan entre teléfonos a
través de Supabase; `supabase.sql` prepara la tabla `finanzas` una sola vez (mismo
proyecto que el Habit Tracker). La app muestra su versión y avisa cuando hay una
nueva; actualizar nunca toca los datos. Abierta como página de Claude, guarda en
la nube de Claude.

Tiene un plan opcional **Billuyo Plus** (análisis completo, historial, categorías
propias, más metas y Excel), ligado a la cuenta; `plus.sql` crea su tabla. La política de
privacidad y los términos están en `privacidad.html`.

Desarrollada con Claude con la misma estructura que
[Habit-tracker](https://github.com/Gads4532/Habit-tracker); el banco de ideas vive en
[Gads4532/Skills](https://github.com/Gads4532/Skills/tree/main/apps/finanzas).
Instalable desde https://gads4532.github.io/Finanzas-personales/ (repo público
[Gads4532/finanzas-personales](https://github.com/Gads4532/Finanzas-personales)).
