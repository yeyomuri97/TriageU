package edu.unicauca.aplimovil.composelble4.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class UserProfile(
    @DocumentId val id: String = "",
    val displayName: String = "",
    val email: String? = null,
    val setupComplete: Boolean = false,
    val availableTimeToday: String = "0 h",
    @ServerTimestamp val createdAt: Timestamp? = null
)

data class FirestoreSubject(
    @DocumentId val id: String = "",
    val name: String = "",
    val color: String = "#4F7A68",
    @ServerTimestamp val createdAt: Timestamp? = null
)

enum class FirestorePriority { HIGH, MEDIUM, LOW }

enum class ActivityType {
    PARCIAL, ENTREGA, EXPOSICION, TALLER, PROYECTO, OTRO
}

data class FirestoreActivity(
    @DocumentId val id: String = "",
    val title: String = "",
    val subjectId: String = "",
    val subjectName: String = "",
    val type: String = ActivityType.OTRO.name,
    val durationMin: Int = 30,
    val priority: String = FirestorePriority.MEDIUM.name,
    val weightPercent: Int? = null,
    val preparation: Int = 3,
    val dueDate: Timestamp? = null,
    val dueText: String = "",
    val completed: Boolean = false,
    val recommended: Boolean = false,
    // Campos extra recomendados
    val notes: String = "",
    val reminderEnabled: Boolean = true,
    val estimatedDifficulty: Int = 3,       // 1-5
    val actualTimeSpent: Int? = null,       // minutos reales
    val tags: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null,
    val completedAt: Timestamp? = null
)

data class FirestoreAvailability(
    @DocumentId val id: String = "",
    val day: String = "",
    val hours: String = "0 h",
    val minutes: Int = 0
)

data class FirestoreProgress(
    val blocksDone: Int = 0,
    val blocksTotal: Int = 10,
    val studiedTimeMin: Int = 0,
    val activeDays: Int = 0,
    val weekDays: Map<String, Boolean> = mapOf(
        "L" to false, "M" to false, "X" to false,
        "J" to false, "V" to false, "S" to false, "D" to false
    ),
    val consistencyChange: String = "",
    val progressMessage: String = "",
    @ServerTimestamp val lastUpdated: Timestamp? = null
)
