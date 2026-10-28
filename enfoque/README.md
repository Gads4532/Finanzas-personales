# Enfoque

Menos teléfono, más de lo que importa. Cada vez que tomas el teléfono, Enfoque pregunta
**«¿Para qué?»**: algo concreto, una sesión de enfoque, una alternativa a la costumbre
o una distracción, que se abre solo después de una espera, con límite diario.
Debajo están tus hábitos y tareas pendientes de hoy del
[Habit Tracker](https://github.com/Gads4532/habit-tracker). Un solo archivo, sin dependencias.

Se instala desde Chrome («Instalar aplicación») y funciona sin conexión. Los datos se guardan
en el teléfono; «Guardar copia» exporta todo a un archivo .json.

## Cómo se conecta con el Habit Tracker

Las dos apps viven en `gads4532.github.io`, así que comparten el almacenamiento del navegador:
Enfoque lee los hábitos y tareas que el Habit Tracker guardó en el teléfono y, si iniciaste
sesión allí, también la versión más reciente de tu cuenta (tabla `estados` de Supabase). No hace
falta otra cuenta. Enfoque **solo lee**: para marcar un hábito o completar una tarea abre el Habit Tracker.

## Lo que una web puede y no puede hacer

Una PWA no puede bloquear otras apps ni medir el tiempo de pantalla de Instagram o TikTok. El
desincentivo real es quitar esas apps de la pantalla de inicio y poner Enfoque en su lugar
(la bienvenida lo explica). El tiempo en distracciones se estima entre que sales por la puerta
de Enfoque y vuelves. Para un tope estricto, se combina con Bienestar digital de Android.

## Publicar

En Settings → Pages, «Source» debe ser «GitHub Actions». Cada cambio en `main` se publica en
https://gads4532.github.io/Enfoque/.

Desarrollada con Claude con la misma estructura que el Habit Tracker y Billuyo; el banco de ideas está en `IDEAS.md`.
