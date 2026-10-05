package edu.unicauca.aplimovil.composelble4.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val uid: String,
    val displayName: String = "",
    val email: String? = null,
    val setupComplete: Boolean = false,
    val availableTimeToday: String = "0 h",
    val createdAt: Long? = null
)