package edu.unicauca.aplimovil.composelble4.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


// ----------------------------------------------------
// MODELOS
// ----------------------------------------------------

data class Subject(
    val id: Int,
    val name: String
)

data class Availability(
    val day: String,
    val hours: String
)

enum class Priority {
    HIGH,
    MEDIUM,
    LOW
}

data class ActivityItem(
    val id: Int,
    val title: String,
    val subject: String,
    val durationMin: Int,
    val priority: Priority,
    val completed: Boolean = false,
    val recommended: Boolean = false,
    val dueText: String = "",
    val weightPercent: Int? = null,
    val preparation: Int = 3
)

data class TimelineItem(
    val id: Int,
    val dateLabel: String,
    val title: String,
    val meta: String,
    val isToday: Boolean = false,
    val isAlert: Boolean = false,
    val badge: String = "",
    val detail: String = ""
)

data class ProgressDay(
    val label: String,
    val hasActivity: Boolean
)

data class WeekBar(
    val label: String,
    val blocks: Int,
    val maxBlocks: Int = 10
)


// ----------------------------------------------------
// ESTADO DE LA APLICACIÓN
// ----------------------------------------------------

data class AppUiState(

    // ------------------------------------------------
    // CONFIGURACIÓN INICIAL
    // ------------------------------------------------

    val isSetupComplete: Boolean = false,

    val subjects: List<Subject> = listOf(
        Subject(
            id = 1,
            name = "Comunicaciones Móviles"
        ),
        Subject(
            id = 2,
            name = "Aplicaciones Móviles"
        ),
        Subject(
            id = 3,
            name = "Entornos Inteligentes"
        )
    ),

    val availability: List<Availability> = listOf(
        Availability(
            day = "Lunes",
            hours = "2 h"
        ),
        Availability(
            day = "Martes",
            hours = "3 h"
        ),
        Availability(
            day = "Miércoles",
            hours = "1 h 30 min"
        )
    ),


    // ------------------------------------------------
    // HOY
    // ------------------------------------------------

    val availableTime: String =
        "2 h 30 min",

    val plannedTime: String =
        "2 h 15 min",

    val todayActivities: List<ActivityItem> = listOf(

        ActivityItem(
            id = 1,
            title = "Parcial de Comunicaciones Móviles",
            subject = "Comunicaciones Móviles",
            durationMin = 60,
            priority = Priority.HIGH,
            recommended = true,
            dueText = "En 2 días",
            weightPercent = 30,
            preparation = 2
        ),

        ActivityItem(
            id = 2,
            title = "Prototipo de App Móvil",
            subject = "Aplicaciones Móviles",
            durationMin = 45,
            priority = Priority.MEDIUM,
            recommended = true,
            dueText = "Entrega mañana",
            weightPercent = 15,
            preparation = 3
        ),

        ActivityItem(
            id = 3,
            title = "Exposición de Metodología",
            subject = "Metodología",
            durationMin = 30,
            priority = Priority.LOW,
            recommended = true,
            dueText = "En 3 días",
            weightPercent = 10,
            preparation = 5
        )
    ),


    // ------------------------------------------------
    // MI RUTA
    // ------------------------------------------------

    val timeline: List<TimelineItem> = listOf(

        TimelineItem(
            id = 1,
            dateLabel = "HOY",
            title = "Comunicaciones Móviles",
            meta = "Empieza a preparar parcial",
            isToday = true,
            detail = "Faltan 2 días"
        ),

        TimelineItem(
            id = 2,
            dateLabel = "LUNES",
            title = "Comunicaciones Móviles",
            meta = "30 % de la materia",
            badge = "PARCIAL"
        ),

        TimelineItem(
            id = 3,
            dateLabel = "MARTES",
            title = "Entrega prototipo",
            meta = "Aplicaciones Móviles",
            detail = "15 %"
        ),

        TimelineItem(
            id = 4,
            dateLabel = "",
            title = "Carga alta",
            meta = "",
            isAlert = true
        ),

        TimelineItem(
            id = 5,
            dateLabel = "VIERNES",
            title = "Proyecto IoT",
            meta = "Entrega"
        ),

        TimelineItem(
            id = 6,
            dateLabel = "PRÓXIMA SEMANA",
            title = "Exposición Metodología",
            meta = ""
        )
    ),


    // ------------------------------------------------
    // PROGRESO
    // ------------------------------------------------

    val progressBlocksDone: Int =
        8,

    val progressBlocksTotal: Int =
        10,

    val studiedTime: String =
        "6 h 20 min",

    val activeDays: Int =
        4,

    val profileProgress: Int =
        0,

    val weekDays: List<ProgressDay> = listOf(
        ProgressDay("L", true),
        ProgressDay("M", true),
        ProgressDay("X", false),
        ProgressDay("J", true),
        ProgressDay("V", true),
        ProgressDay("S", false),
        ProgressDay("D", false)
    ),

    val consistencyChange: String =
        "↑ 15 % más constancia que la semana pasada",

    val progressMessage: String =
        "Completaste dos bloques más que la semana pasada.",

    val weekTrend: List<WeekBar> = listOf(
        WeekBar("S1", 5),
        WeekBar("S2", 6),
        WeekBar("S3", 7),
        WeekBar("S4", 8)
    ),


    // ------------------------------------------------
    // NUEVA ACTIVIDAD
    // ------------------------------------------------

    val newActivityTitle: String =
        "",

    val newActivitySubject: String =
        "Comunicaciones Móviles",

    val newActivityType: String =
        "Parcial",

    val newActivityDate: String =
        "05 / 09 / 2026",

    val newActivityWeight: String =
        "30",

    val newActivityPreparation: Int =
        2,

    val newActivityEstimatedTime: String =
        "4 horas"
)


// ----------------------------------------------------
// VIEWMODEL
// ----------------------------------------------------

class AppViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            AppUiState()
        )

    val uiState: StateFlow<AppUiState> =
        _uiState.asStateFlow()


    // ------------------------------------------------
    // CONFIGURACIÓN INICIAL
    // ------------------------------------------------

    fun completeSetup() {

        _uiState.update {

            it.copy(
                isSetupComplete = true
            )
        }
    }


    fun skipSetup() {

        _uiState.update {

            it.copy(
                isSetupComplete = true
            )
        }
    }


    fun addSubject(
        name: String
    ) {

        if (name.isBlank()) {
            return
        }

        _uiState.update { state ->

            val nextId =
                (state.subjects
                    .maxOfOrNull { it.id } ?: 0) + 1

            state.copy(

                subjects =
                    state.subjects +
                            Subject(
                                id = nextId,
                                name = name.trim()
                            )
            )
        }
    }


    // ------------------------------------------------
    // HOY
    // ------------------------------------------------

    fun toggleActivityCompleted(
        id: Int
    ) {

        _uiState.update { state ->

            val updatedActivities =
                state.todayActivities.map { activity ->

                    if (activity.id == id) {

                        activity.copy(
                            completed = !activity.completed
                        )

                    } else {

                        activity
                    }
                }

            val completedActivities =
                updatedActivities.count { it.completed }

            val progress =
                if (updatedActivities.isEmpty()) {
                    0
                } else {
                    (completedActivities * 100) / updatedActivities.size
                }

            state.copy(
                todayActivities = updatedActivities,
                profileProgress = progress
            )
        }
    }


    // ------------------------------------------------
    // NUEVA ACTIVIDAD
    // ------------------------------------------------

    fun updateNewActivityTitle(
        value: String
    ) {

        _uiState.update {

            it.copy(
                newActivityTitle = value
            )
        }
    }


    fun updateNewActivitySubject(
        value: String
    ) {

        _uiState.update {

            it.copy(
                newActivitySubject = value
            )
        }
    }


    fun updateNewActivityType(
        value: String
    ) {

        _uiState.update {

            it.copy(
                newActivityType = value
            )
        }
    }


    fun updateNewActivityDate(
        value: String
    ) {

        _uiState.update {

            it.copy(
                newActivityDate = value
            )
        }
    }


    fun updateNewActivityWeight(
        value: String
    ) {

        _uiState.update {

            it.copy(
                newActivityWeight = value
            )
        }
    }


    fun updateNewActivityPreparation(
        value: Int
    ) {

        if (value !in 1..5) {
            return
        }

        _uiState.update {

            it.copy(
                newActivityPreparation = value
            )
        }
    }


    fun updateNewActivityEstimatedTime(
        value: String
    ) {

        _uiState.update {

            it.copy(
                newActivityEstimatedTime = value
            )
        }
    }


    // ------------------------------------------------
    // GUARDAR ACTIVIDAD
    // ------------------------------------------------

    fun saveNewActivity() {

        _uiState.update { state ->

            val title =
                state.newActivityTitle
                    .ifBlank {
                        "Nueva actividad"
                    }

            val subject =
                state.newActivitySubject
                    .ifBlank {

                        state.subjects
                            .firstOrNull()
                            ?.name
                            ?: "General"
                    }

            val weight =
                state.newActivityWeight
                    .toIntOrNull()

            val calculatedPriority =
                calculatePriority(
                    preparation =
                        state.newActivityPreparation,
                    weight =
                        weight
                )

            val duration =
                estimatedTimeToMinutes(
                    state.newActivityEstimatedTime
                )

            val nextId =
                (state.todayActivities
                    .maxOfOrNull {
                        it.id
                    } ?: 0) + 1

            state.copy(

                todayActivities =
                    state.todayActivities +
                            ActivityItem(
                                id = nextId,
                                title = title,
                                subject = subject,
                                durationMin = duration,
                                priority = calculatedPriority,
                                recommended = true,
                                dueText =
                                    state.newActivityDate,
                                weightPercent =
                                    weight,
                                preparation =
                                    state.newActivityPreparation
                            ),

                newActivityTitle =
                    "",

                newActivitySubject =
                    state.subjects
                        .firstOrNull()
                        ?.name
                        ?: "",

                newActivityType =
                    "Parcial",

                newActivityDate =
                    "05 / 09 / 2026",

                newActivityWeight =
                    "",

                newActivityPreparation =
                    3,

                newActivityEstimatedTime =
                    "4 horas"
            )
        }
    }


    // ------------------------------------------------
    // PRIORIDAD RECOMENDADA
    // ------------------------------------------------

    private fun calculatePriority(
        preparation: Int,
        weight: Int?
    ): Priority {

        val activityWeight =
            weight ?: 0

        return when {

            preparation <= 2 ||
                    activityWeight >= 30 -> {

                Priority.HIGH
            }

            preparation == 3 ||
                    activityWeight >= 15 -> {

                Priority.MEDIUM
            }

            else -> {

                Priority.LOW
            }
        }
    }


    // ------------------------------------------------
    // TIEMPO ESTIMADO
    // ------------------------------------------------

    private fun estimatedTimeToMinutes(
        value: String
    ): Int {

        if (value == "30 minutos") {
            return 30
        }

        val hours =
            value
                .substringBefore(" ")
                .replace(",", ".")
                .toDoubleOrNull()
                ?: 1.0

        return (hours * 60).toInt()
    }
}