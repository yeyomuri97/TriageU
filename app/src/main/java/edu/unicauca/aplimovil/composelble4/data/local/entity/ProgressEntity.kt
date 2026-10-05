package edu.unicauca.aplimovil.composelble4.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "progress",
    indices = [
        Index(value = ["uid"], unique = true)
    ]
)
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val uid: String,

    val blocksDone: Int = 0,
    val blocksTotal: Int = 10,
    val studiedTimeMin: Int = 0,
    val activeDays: Int = 0,

    val weekDaysJson: String = "{}",

    val consistencyChange: String = "",
    val progressMessage: String = "",

    val lastUpdated: Long? = null
)