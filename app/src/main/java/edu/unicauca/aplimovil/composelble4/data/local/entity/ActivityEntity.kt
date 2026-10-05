package edu.unicauca.aplimovil.composelble4.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "activities",
    indices = [
        Index(value = ["uid"]),
        Index(value = ["uid", "firestoreId"], unique = true),
        Index(value = ["subjectId"])
    ]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val uid: String,
    val firestoreId: String? = null,

    val title: String,

    val subjectId: String = "",
    val subjectName: String = "",

    val type: String = "OTRO",
    val durationMin: Int = 30,
    val priority: String = "MEDIUM",

    val weightPercent: Int? = null,
    val preparation: Int = 3,

    val dueDate: Long? = null,
    val dueText: String = "",

    val completed: Boolean = false,
    val recommended: Boolean = false,

    val notes: String = "",
    val reminderEnabled: Boolean = true,
    val estimatedDifficulty: Int = 3,
    val actualTimeSpent: Int? = null,

    val tagsJson: String = "[]",

    val createdAt: Long? = null,
    val completedAt: Long? = null
)