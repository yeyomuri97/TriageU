package edu.unicauca.aplimovil.composelble4.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "availability",
    indices = [
        Index(value = ["uid"]),
        Index(value = ["uid", "day"], unique = true)
    ]
)
data class AvailabilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val uid: String,
    val firestoreId: String? = null,

    val day: String,
    val hours: String = "0 h",
    val minutes: Int = 0
)