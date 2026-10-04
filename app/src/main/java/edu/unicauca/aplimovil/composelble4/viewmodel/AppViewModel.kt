package edu.unicauca.aplimovil.composelble4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.composelble4.data.mapper.parseDueDate
import edu.unicauca.aplimovil.composelble4.data.mapper.toFirestore
import edu.unicauca.aplimovil.composelble4.data.mapper.toUi
import edu.unicauca.aplimovil.composelble4.data.model.UserProfile
import edu.unicauca.aplimovil.composelble4.data.repository.TriageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ----------------------------------------------------
// MODELOS DE UI (compatibles con pantallas existentes + campos extra)
// ----------------------------------------------------

data class Subject(
    val id: Int,
    val name: String,
    val firestoreId: String = ""
)

data class Availability(
    val day: String,
    val hours: String
)

enum class Priority {
    HIGH, MEDIUM, LOW
}

data class ActivityItem(
    val id: Int,
    val firestoreId: String = "",
    val title: String,
    val subject: String,
    val subjectId: String = "",
    val type: String = "OTRO",
    val durationMin: Int,
    val priority: Priority,
    val completed: Boolean = false,
    val recommended: Boolean = false,
    val dueText: String = "",
    val weightPercent: Int? = null,
    val preparation: Int = 3,
    // Campos extra
    val notes: String = "",
    val reminderEnabled: Boolean = true,
    val estimatedDifficulty: Int = 3,
    val actualTimeSpent: Int? = null,
    val tags: List<String> = emptyList()
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
// ESTADO
// ----------------------------------------------------

data class AppUiState(
    val isSetupComplete: Boolean = false,
    val isLoading: Boolean = false,
    val isSynced: Boolean = false,
    val errorMessage: String? = null,

    val subjects: List<Subject> = listOf(
        Subject(id = 1, name = "Comunicaciones Móviles"),
        Subject(id = 2, name = "Aplicaciones Móviles"),
        Subject(id = 3, name = "Entornos Inteligentes")
    ),

    val availability: List<Availability> = listOf(
        Availability(day = "Lunes", hours = "2 h"),
        Availability(day = "Martes", hours = "3 h"),
        Availability(day = "Miércoles", hours = "1 h 30 min")
    ),

    val availableTime: String = "2 h 30 min",
    val plannedTime: String = "2 h 15 min",

    val todayActivities: List<ActivityItem> = listOf(
        ActivityItem(
            id = 1,
            title = "Parcial de Comunicaciones Móviles",
            subject = "Comunicaciones Móviles",
            type = "PARCIAL",
            durationMin = 60,
            priority = Priority.HIGH,
            recommended = true,
            dueText = "En 2 días",
            weightPercent = 30,
            preparation = 2,
            estimatedDifficulty = 4,
            tags = listOf("urgente")
        ),
        ActivityItem(
            id = 2,
            title = "Prototipo de App Móvil",
            subject = "Aplicaciones Móviles",
            type = "ENTREGA",
            durationMin = 45,
            priority = Priority.MEDIUM,
            recommended = true,
            dueText = "Entrega mañana",
            weightPercent = 15,
            preparation = 3,
            estimatedDifficulty = 3
        ),
        ActivityItem(
            id = 3,
            title = "Exposición de Metodología",
            subject = "Metodología",
            type = "EXPOSICION",
            durationMin = 30,
            priority = Priority.LOW,
            recommended = true,
            dueText = "En 3 días",
            weightPercent = 10,
            preparation = 5,
            estimatedDifficulty = 2
        )
    ),

    val timeline: List<TimelineItem> = listOf(
        TimelineItem(1, "HOY", "Comunicaciones Móviles", "Empieza a preparar parcial", isToday = true, detail = "Faltan 2 días"),
        TimelineItem(2, "LUNES", "Comunicaciones Móviles", "30 % de la materia", badge = "PARCIAL"),
        TimelineItem(3, "MARTES", "Entrega prototipo", "Aplicaciones Móviles", detail = "15 %"),
        TimelineItem(4, "", "Carga alta", "", isAlert = true),
        TimelineItem(5, "VIERNES", "Proyecto IoT", "Entrega"),
        TimelineItem(6, "PRÓXIMA SEMANA", "Exposición Metodología", "")
    ),

    val progressBlocksDone: Int = 8,
    val progressBlocksTotal: Int = 10,
    val studiedTime: String = "6 h 20 min",
    val activeDays: Int = 4,
    val profileProgress: Int = 0,
    val weekDays: List<ProgressDay> = listOf(
        ProgressDay("L", true), ProgressDay("M", true), ProgressDay("X", false),
        ProgressDay("J", true), ProgressDay("V", true), ProgressDay("S", false), ProgressDay("D", false)
    ),
    val consistencyChange: String = "↑ 15 % más constancia que la semana pasada",
    val progressMessage: String = "Completaste dos bloques más que la semana pasada.",
    val weekTrend: List<WeekBar> = listOf(
        WeekBar("S1", 5), WeekBar("S2", 7), WeekBar("S3", 6), WeekBar("S4", 8)
    ),

    // Formulario nueva actividad
    val newActivityTitle: String = "",
    val newActivitySubject: String = "Comunicaciones Móviles",
    val newActivityType: String = "Parcial",
    val newActivityDate: String = "05 / 09 / 2026",
    val newActivityWeight: String = "",
    val newActivityPreparation: Int = 3,
    val newActivityEstimatedTime: String = "4 horas",
    val newActivityNotes: String = "",
    val newActivityDifficulty: Int = 3,
    val newActivityReminder: Boolean = true
)

// ----------------------------------------------------
// VIEWMODEL
// ----------------------------------------------------

class AppViewModel(
    private val repository: TriageRepository = TriageRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        // Conectar con Firebase al iniciar
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.ensureUser()
                // Guardar perfil básico si es primera vez
                val profile = repository.getUserProfile()
                if (profile == null) {
                    repository.saveUserProfile(
                        UserProfile(
                            displayName = "Estudiante",
                            setupComplete = false
                        )
                    )
                } else {
                    _uiState.update { it.copy(isSetupComplete = profile.setupComplete) }
                }
                // Observar actividades en tiempo real
                launch {
                    repository.observeActivities().collect { list ->
                        if (list.isNotEmpty()) {
                            val uiList = list.mapIndexed { i, a -> a.toUi(i + 1) }
                            _uiState.update { state ->
                                state.copy(
                                    todayActivities = uiList,
                                    isSynced = true,
                                    isLoading = false
                                )
                            }
                        } else {
                            _uiState.update { it.copy(isLoading = false, isSynced = true) }
                        }
                    }
                }
                // Observar materias
                launch {
                    repository.observeSubjects().collect { list ->
                        if (list.isNotEmpty()) {
                            val uiList = list.mapIndexed { i, s -> s.toUi(i + 1) }
                            _uiState.update { it.copy(subjects = uiList) }
                        }
                    }
                }
            } catch (e: Exception) {
                // Si falla la red, se mantienen los datos locales de ejemplo
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al conectar con Firebase"
                    )
                }
            }
        }
    }

    // ------------------------------------------------
    // SETUP
    // ------------------------------------------------

    fun completeSetup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSetupComplete = true) }
            try {
                repository.saveUserProfile(
                    UserProfile(
                        displayName = "Estudiante",
                        setupComplete = true,
                        availableTimeToday = _uiState.value.availableTime
                    )
                )
                // Subir materias y disponibilidad iniciales
                _uiState.value.subjects.forEach { subject ->
                    if (subject.firestoreId.isBlank()) {
                        repository.addSubject(subject.toFirestore())
                    }
                }
                repository.saveAvailability(
                    _uiState.value.availability.map { it.toFirestore() }
                )
            } catch (_: Exception) { /* offline: se queda local */ }
        }
    }

    // ------------------------------------------------
    // ACTIVIDADES
    // ------------------------------------------------
    fun skipSetup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSetupComplete = true) }
            try {
                repository.saveUserProfile(
                    UserProfile(
                        displayName = "Estudiante",
                        setupComplete = true,
                        availableTimeToday = _uiState.value.availableTime
                    )
                )
            } catch (_: Exception) { }
        }
    }

    fun toggleActivityComplete(activityId: Int) {
        val activity = _uiState.value.todayActivities.find { it.id == activityId } ?: return
        val newCompleted = !activity.completed

        // Actualización optimista local
        _uiState.update { state ->
            state.copy(
                todayActivities = state.todayActivities.map {
                    if (it.id == activityId) it.copy(completed = newCompleted) else it
                }
            )
        }

        // Sincronizar con Firestore
        viewModelScope.launch {
            try {
                if (activity.firestoreId.isNotBlank()) {
                    repository.toggleActivityCompleted(activity.firestoreId, newCompleted)
                }
            } catch (_: Exception) { }
        }
    }

    fun updateNewActivityTitle(value: String) {
        _uiState.update { it.copy(newActivityTitle = value) }
    }

    fun updateNewActivitySubject(value: String) {
        _uiState.update { it.copy(newActivitySubject = value) }
    }

    fun updateNewActivityType(value: String) {
        _uiState.update { it.copy(newActivityType = value) }
    }

    fun updateNewActivityDate(value: String) {
        _uiState.update { it.copy(newActivityDate = value) }
    }

    fun updateNewActivityWeight(value: String) {
        _uiState.update { it.copy(newActivityWeight = value) }
    }

    fun updateNewActivityPreparation(value: Int) {
        _uiState.update { it.copy(newActivityPreparation = value) }
    }

    fun updateNewActivityEstimatedTime(value: String) {
        _uiState.update { it.copy(newActivityEstimatedTime = value) }
    }

    fun updateNewActivityNotes(value: String) {
        _uiState.update { it.copy(newActivityNotes = value) }
    }

    fun updateNewActivityDifficulty(value: Int) {
        _uiState.update { it.copy(newActivityDifficulty = value) }
    }

    fun updateNewActivityReminder(value: Boolean) {
        _uiState.update { it.copy(newActivityReminder = value) }
    }

    fun saveNewActivity() {
        val state = _uiState.value
        val title = state.newActivityTitle.trim()
        if (title.isBlank()) return

        val weight = state.newActivityWeight.toIntOrNull()
        val duration = estimatedTimeToMinutes(state.newActivityEstimatedTime)
        val priority = calculatePriority(state.newActivityPreparation, weight)
        val typeNormalized = state.newActivityType.uppercase()
            .replace(" ", "_")
            .replace("Ó", "O")
            .replace("É", "E")

        val nextId = (state.todayActivities.maxOfOrNull { it.id } ?: 0) + 1

        val newItem = ActivityItem(
            id = nextId,
            title = title,
            subject = state.newActivitySubject,
            type = typeNormalized,
            durationMin = duration,
            priority = priority,
            recommended = true,
            dueText = state.newActivityDate,
            weightPercent = weight,
            preparation = state.newActivityPreparation,
            notes = state.newActivityNotes,
            reminderEnabled = state.newActivityReminder,
            estimatedDifficulty = state.newActivityDifficulty
        )

        // Local primero
        _uiState.update {
            it.copy(
                todayActivities = it.todayActivities + newItem,
                newActivityTitle = "",
                newActivitySubject = it.subjects.firstOrNull()?.name ?: "",
                newActivityType = "Parcial",
                newActivityDate = "05 / 09 / 2026",
                newActivityWeight = "",
                newActivityPreparation = 3,
                newActivityEstimatedTime = "4 horas",
                newActivityNotes = "",
                newActivityDifficulty = 3,
                newActivityReminder = true
            )
        }

        // Guardar en Firestore
        viewModelScope.launch {
            try {
                val due = parseDueDate(newItem.dueText)
                val firestoreId = repository.addActivity(newItem.toFirestore(dueDate = due))
                // Actualizar el firestoreId local
                _uiState.update { s ->
                    s.copy(
                        todayActivities = s.todayActivities.map {
                            if (it.id == nextId) it.copy(firestoreId = firestoreId) else it
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    // ------------------------------------------------
    // HELPERS
    // ------------------------------------------------

    private fun calculatePriority(preparation: Int, weight: Int?): Priority {
        val w = weight ?: 0
        return when {
            preparation <= 2 || w >= 30 -> Priority.HIGH
            preparation == 3 || w >= 15 -> Priority.MEDIUM
            else -> Priority.LOW
        }
    }

    private fun estimatedTimeToMinutes(value: String): Int {
        if (value == "30 minutos") return 30
        val hours = value.substringBefore(" ").replace(",", ".").toDoubleOrNull() ?: 1.0
        return (hours * 60).toInt()
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
