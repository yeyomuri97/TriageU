package edu.unicauca.aplimovil.composelble4.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "subjects",
    indices = [
        Index(value = ["uid"]),
        Index(value = ["uid", "firestoreId"], unique = true)
    ]
)
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val uid: String,
    val firestoreId: String? = null,
    val name: String,
    val color: String = "#4F7A68",
    val createdAt: Long? = null
)