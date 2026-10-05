# Firebase en TriageU – Guía rápida

## Ya configurado en el código
- Auth anónimo
- Firestore (actividades, materias, disponibilidad, progreso)
- Modelos con campos extra (dueDate, type, notes, reminderEnabled, estimatedDifficulty, actualTimeSpent, tags, createdAt, completedAt)
- Sincronización en tiempo real desde AppViewModel

## Checklist en Firebase Console
1. Authentication → Sign-in method → **Anonymous** habilitado
2. Firestore Database creada
3. Rules publicadas (ver `firestore.rules`)
4. `google-services.json` en carpeta `app/`

## Estructura Firestore
```
users/{uid}
  subjects/{id}
  activities/{id}
  availability/{day}
  progress/summary
```

## Uso
Al abrir la app se crea un usuario anónimo automáticamente.
Las actividades nuevas se guardan en Firestore.
Si no hay red, se usan los datos locales de ejemplo.
