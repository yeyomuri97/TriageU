package edu.unicauca.aplimovil.composelble4.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Subject(
    val id: Int,
    val name: String
)

data class Availability(
    val day: String,
    val hours: String
)

data class ActivityItem(
    val id: Int,
    val title: String,
    val subject: String,
    val durationMin: Int,
    val priority: Priority,
    val completed: Boolean = false,
    val recommended: Boolean = false
)

enum class Priority { HIGH, MEDIUM, LOW }

data class TimelineItem(
    val id: Int,
    val dateLabel: String,
    val title: String,
    val meta: String,
    val isToday: Boolean = false,
    val isAlert: Boolean = false
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

data class AppUiState(
    val isSetupComplete: Boolean = false,
    val subjects: List<Subject> = listOf(
        Subject(1, "Comunicaciones Móviles"),
        Subject(2, "Aplicaciones Móviles"),
        Subject(3, "Entornos Inteligentes")
    ),
    val availability: List<Availability> = listOf(
        Availability("Lunes", "2 h"),
        Availability("Martes", "3 h"),
        Availability("Miércoles", "1 h 30 min")
    ),
    val todayActivities: List<ActivityItem> = listOf(
        ActivityItem(1, "Preparar parcial", "Comunicaciones Móviles", 60, Priority.HIGH, recommended = true),
        ActivityItem(2, "Avanzar prototipo", "Aplicaciones Móviles", 45, Priority.MEDIUM, recommended = true),
        ActivityItem(3, "Revisar apuntes", "Entornos Inteligentes", 30, Priority.LOW)
    ),
    val timeline: List<TimelineItem> = listOf(
        TimelineItem(1, "HOY", "Momento recomendado para comenzar a preparar Comunicaciones Móviles", "", isToday = true),
        TimelineItem(2, "LUN 31", "Parcial de Comunicaciones Móviles", "30 % de la materia"),
        TimelineItem(3, "MAR 1", "Entrega del prototipo de Aplicaciones Móviles", "15 %"),
        TimelineItem(4, "⚠ Carga alta", "Dos compromisos importantes se concentran en 48 horas", "", isAlert = true),
        TimelineItem(5, "VIE 4", "Entrega del proyecto de Entornos Inteligentes", ""),
        TimelineItem(6, "PRÓXIMA SEMANA", "Exposición de Metodología", "")
    ),
    val progressBlocksDone: Int = 8,
    val progressBlocksTotal: Int = 10,
    val studiedTime: String = "6 h 20 min",
    val activeDays: Int = 4,
    val weekDays: List<ProgressDay> = listOf(
        ProgressDay("L", true),
        ProgressDay("M", true),
        ProgressDay("X", false),
        ProgressDay("J", true),
        ProgressDay("V", true),
        ProgressDay("S", false),
        ProgressDay("D", false)
    ),
    val consistencyChange: String = "+15 % de constancia vs. semana pasada",
    val progressMessage: String = "Buen avance: completaste dos bloques más que la semana pasada.",
    val weekTrend: List<WeekBar> = listOf(
        WeekBar("S1", 5),
        WeekBar("S2", 6),
        WeekBar("S3", 7),
        WeekBar("S4", 8)
    ),
    val newActivityTitle: String = "",
    val newActivitySubject: String = "",
    val newActivityDuration: String = "30",
    val newActivityPriority: Priority = Priority.MEDIUM
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun completeSetup() {
        _uiState.update { it.copy(isSetupComplete = true) }
    }

    fun skipSetup() {
        _uiState.update { it.copy(isSetupComplete = true) }
    }

    fun addSubject(name: String) {
        if (name.isBlank()) return
        _uiState.update { state ->
            val nextId = (state.subjects.maxOfOrNull { it.id } ?: 0) + 1
            state.copy(subjects = state.subjects + Subject(nextId, name.trim()))
        }
    }

    fun toggleActivityCompleted(id: Int) {
        _uiState.update { state ->
            state.copy(
                todayActivities = state.todayActivities.map {
                    if (it.id == id) it.copy(completed = !it.completed) else it
                }
            )
        }
    }

    fun updateNewActivityTitle(value: String) {
        _uiState.update { it.copy(newActivityTitle = value) }
    }

    fun updateNewActivitySubject(value: String) {
        _uiState.update { it.copy(newActivitySubject = value) }
    }

    fun updateNewActivityDuration(value: String) {
        _uiState.update { it.copy(newActivityDuration = value) }
    }

    fun updateNewActivityPriority(priority: Priority) {
        _uiState.update { it.copy(newActivityPriority = priority) }
    }

    fun saveNewActivity() {
        _uiState.update { state ->
            val title = state.newActivityTitle.ifBlank { "Nueva actividad" }
            val subject = state.newActivitySubject.ifBlank { state.subjects.firstOrNull()?.name ?: "General" }
            val duration = state.newActivityDuration.toIntOrNull() ?: 30
            val nextId = (state.todayActivities.maxOfOrNull { it.id } ?: 0) + 1
            state.copy(
                todayActivities = state.todayActivities + ActivityItem(
                    id = nextId,
                    title = title,
                    subject = subject,
                    durationMin = duration,
                    priority = state.newActivityPriority
                ),
                newActivityTitle = "",
                newActivitySubject = "",
                newActivityDuration = "30",
                newActivityPriority = Priority.MEDIUM
            )
        }
    }
}
