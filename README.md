# TriageU

App Android para priorizar el tiempo de estudio. Ayuda al estudiante a organizar materias, ver qué hacer hoy, anticipar compromisos y seguir su progreso — sin rachas rígidas ni formularios innecesarios.

**Prioriza tu tiempo. Avanza a tu ritmo.**

## Características

- **Configuración inicial** — Materias y disponibilidad sin registro obligatorio
- **Hoy** — Actividades del día con prioridad (alta / media / baja) y recomendaciones
- **Mi Ruta** — Línea de tiempo de próximos compromisos y alertas de carga
- **Progreso** — Bloques completados, días activos y tendencia semanal
- **Nueva actividad** — Formulario simple desde el botón +
- **Perfil** — Resumen de materias

## Stack técnico

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje |
| Jetpack Compose | UI |
| Material Design 3 | Tema y componentes |
| Navigation Compose | Navegación entre pantallas |
| ViewModel + StateFlow | Estado de la app |

## Estructura del proyecto

```
app/src/main/java/.../composelble4/
├── MainActivity.kt
├── viewmodel/
│   └── AppViewModel.kt
└── ui/
    ├── App.kt
    ├── navigation/
    ├── screens/
    │   ├── SetupScreen.kt
    │   ├── HoyScreen.kt
    │   ├── MiRutaScreen.kt
    │   ├── ProgresoScreen.kt
    │   ├── NuevaActividadScreen.kt
    │   └── PerfilScreen.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## Cómo ejecutar

1. Clona el repositorio
2. Abre la carpeta del proyecto en **Android Studio**
3. Sincroniza Gradle
4. Ejecuta en emulador o dispositivo (minSdk 30)

## Sistema visual

Paleta basada en el diseño del equipo:

| Uso | Color | HEX |
|---|---|---|
| Principal | Verde salvia | `#4F7A68` |
| Verde suave | Fondo / selección | `#EAF4EE` |
| Fondo | Blanco verdoso | `#F8FBF9` |
| Texto | Gris oscuro | `#1F2937` |
| Secundario | Gris | `#667085` |

## Principios de diseño

1. Diseñar con intención  
2. Navegación fácil y natural  
3. Ser claros y directos  
4. Mostrar valor antes del registro  
5. Formularios simples y flexibles  
6. Dar control y flexibilidad  
7. Dar retroalimentación  
8. Diseñar para todos  
9. Mantener consistencia  
10. Privacidad y permisos  

## Licencia

Proyecto académico — Universidad del Cauca.
