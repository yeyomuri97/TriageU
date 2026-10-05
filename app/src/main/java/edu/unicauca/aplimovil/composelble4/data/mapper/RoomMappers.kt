package edu.unicauca.aplimovil.composelble4.data.mapper

import com.google.firebase.Timestamp
import edu.unicauca.aplimovil.composelble4.data.local.entity.ActivityEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.AvailabilityEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.ProgressEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.SubjectEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.UserProfileEntity
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreActivity
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreAvailability
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreProgress
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreSubject
import edu.unicauca.aplimovil.composelble4.data.model.UserProfile
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date

// ----------------------------------------------------
// FECHAS
// ----------------------------------------------------

private fun Timestamp?.toLongOrNull(): Long? {
    return this?.toDate()?.time
}

private fun Long?.toTimestampOrNull(): Timestamp? {
    return this?.let { Timestamp(Date(it)) }
}

// ----------------------------------------------------
// PERFIL
// ----------------------------------------------------

fun UserProfile.toEntity(uid: String): UserProfileEntity {
    return UserProfileEntity(
        uid = uid,
        displayName = displayName,
        email = email,
        setupComplete = setupComplete,
        availableTimeToday = availableTimeToday,
        createdAt = createdAt.toLongOrNull()
    )
}

fun UserProfileEntity.toFirestore(): UserProfile {
    return UserProfile(
        id = uid,
        displayName = displayName,
        email = email,
        setupComplete = setupComplete,
        availableTimeToday = availableTimeToday,
        createdAt = createdAt.toTimestampOrNull()
    )
}

// ----------------------------------------------------
// MATERIAS
// ----------------------------------------------------

fun FirestoreSubject.toEntity(uid: String): SubjectEntity {
    return SubjectEntity(
        uid = uid,
        firestoreId = id.ifBlank { null },
        name = name,
        color = color,
        createdAt = createdAt.toLongOrNull()
    )
}

fun SubjectEntity.toFirestore(): FirestoreSubject {
    return FirestoreSubject(
        id = firestoreId ?: "",
        name = name,
        color = color,
        createdAt = createdAt.toTimestampOrNull()
    )
}

// ----------------------------------------------------
// ACTIVIDADES
// ----------------------------------------------------

fun FirestoreActivity.toEntity(uid: String): ActivityEntity {
    return ActivityEntity(
        uid = uid,
        firestoreId = id.ifBlank { null },
        title = title,
        subjectId = subjectId,
        subjectName = subjectName,
        type = type,
        durationMin = durationMin,
        priority = priority,
        weightPercent = weightPercent,
        preparation = preparation,
        dueDate = dueDate.toLongOrNull(),
        dueText = dueText,
        completed = completed,
        recommended = recommended,
        notes = notes,
        reminderEnabled = reminderEnabled,
        estimatedDifficulty = estimatedDifficulty,
        actualTimeSpent = actualTimeSpent,
        tagsJson = listToJson(tags),
        createdAt = createdAt.toLongOrNull(),
        completedAt = completedAt.toLongOrNull()
    )
}

fun ActivityEntity.toFirestore(): FirestoreActivity {
    return FirestoreActivity(
        id = firestoreId ?: "",
        title = title,
        subjectId = subjectId,
        subjectName = subjectName,
        type = type,
        durationMin = durationMin,
        priority = priority,
        weightPercent = weightPercent,
        preparation = preparation,
        dueDate = dueDate.toTimestampOrNull(),
        dueText = dueText,
        completed = completed,
        recommended = recommended,
        notes = notes,
        reminderEnabled = reminderEnabled,
        estimatedDifficulty = estimatedDifficulty,
        actualTimeSpent = actualTimeSpent,
        tags = jsonToList(tagsJson),
        createdAt = createdAt.toTimestampOrNull(),
        completedAt = completedAt.toTimestampOrNull()
    )
}

// ----------------------------------------------------
// DISPONIBILIDAD
// ----------------------------------------------------

fun FirestoreAvailability.toEntity(uid: String): AvailabilityEntity {
    return AvailabilityEntity(
        uid = uid,
        firestoreId = id.ifBlank { null },
        day = day,
        hours = hours,
        minutes = minutes
    )
}

fun AvailabilityEntity.toFirestore(): FirestoreAvailability {
    return FirestoreAvailability(
        id = firestoreId ?: "",
        day = day,
        hours = hours,
        minutes = minutes
    )
}

// ----------------------------------------------------
// PROGRESO
// ----------------------------------------------------

fun FirestoreProgress.toEntity(uid: String): ProgressEntity {
    return ProgressEntity(
        uid = uid,
        blocksDone = blocksDone,
        blocksTotal = blocksTotal,
        studiedTimeMin = studiedTimeMin,
        activeDays = activeDays,
        weekDaysJson = mapToJson(weekDays),
        consistencyChange = consistencyChange,
        progressMessage = progressMessage,
        lastUpdated = lastUpdated.toLongOrNull()
    )
}

fun ProgressEntity.toFirestore(): FirestoreProgress {
    return FirestoreProgress(
        blocksDone = blocksDone,
        blocksTotal = blocksTotal,
        studiedTimeMin = studiedTimeMin,
        activeDays = activeDays,
        weekDays = jsonToMap(weekDaysJson),
        consistencyChange = consistencyChange,
        progressMessage = progressMessage,
        lastUpdated = lastUpdated.toTimestampOrNull()
    )
}

// ----------------------------------------------------
// JSON SIMPLE PARA ROOM
// ----------------------------------------------------

private fun listToJson(items: List<String>): String {
    val array = JSONArray()

    items.forEach { item ->
        array.put(item)
    }

    return array.toString()
}

private fun jsonToList(value: String): List<String> {
    return try {
        val array = JSONArray(value)

        List(array.length()) { index ->
            array.optString(index)
        }
    } catch (_: Exception) {
        emptyList()
    }
}

private fun mapToJson(items: Map<String, Boolean>): String {
    val json = JSONObject()

    items.forEach { (key, value) ->
        json.put(key, value)
    }

    return json.toString()
}

private fun jsonToMap(value: String): Map<String, Boolean> {
    return try {
        val json = JSONObject(value)
        val result = mutableMapOf<String, Boolean>()

        val keys = json.keys()

        while (keys.hasNext()) {
            val key = keys.next()
            result[key] = json.optBoolean(key, false)
        }

        result
    } catch (_: Exception) {
        emptyMap()
    }
}