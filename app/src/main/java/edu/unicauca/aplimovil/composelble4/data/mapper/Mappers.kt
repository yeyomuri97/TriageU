package edu.unicauca.aplimovil.composelble4.data.mapper

import com.google.firebase.Timestamp
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreActivity
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreAvailability
import edu.unicauca.aplimovil.composelble4.data.model.FirestorePriority
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreSubject
import edu.unicauca.aplimovil.composelble4.viewmodel.ActivityItem
import edu.unicauca.aplimovil.composelble4.viewmodel.Availability
import edu.unicauca.aplimovil.composelble4.viewmodel.Priority
import edu.unicauca.aplimovil.composelble4.viewmodel.Subject
import java.util.Calendar
import java.util.Date

fun FirestoreSubject.toUi(index: Int = 0) = Subject(
    id = id.ifBlank { (index + 1).toString() }.hashCode().let { if (it == Int.MIN_VALUE) index + 1 else kotlin.math.abs(it) },
    name = name,
    firestoreId = id
)

fun Subject.toFirestore() = FirestoreSubject(
    id = firestoreId,
    name = name
)

fun FirestoreAvailability.toUi() = Availability(day = day, hours = hours)

fun Availability.toFirestore() = FirestoreAvailability(
    id = day.lowercase(),
    day = day,
    hours = hours,
    minutes = parseHoursToMinutes(hours)
)

private fun parseHoursToMinutes(value: String): Int {
    val h = Regex("""(\d+)\s*h""").find(value)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    val m = Regex("""(\d+)\s*min""").find(value)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    return h * 60 + m
}

fun FirestoreActivity.toUi(fallbackId: Int = 0) = ActivityItem(
    id = id.ifBlank { fallbackId.toString() }.hashCode().let { if (it == Int.MIN_VALUE) fallbackId else kotlin.math.abs(it) },
    firestoreId = id,
    title = title,
    subject = subjectName,
    subjectId = subjectId,
    type = type,
    durationMin = durationMin,
    priority = when (priority) {
        FirestorePriority.HIGH.name -> Priority.HIGH
        FirestorePriority.LOW.name -> Priority.LOW
        else -> Priority.MEDIUM
    },
    completed = completed,
    recommended = recommended,
    dueText = dueText,
    weightPercent = weightPercent,
    preparation = preparation,
    notes = notes,
    reminderEnabled = reminderEnabled,
    estimatedDifficulty = estimatedDifficulty,
    actualTimeSpent = actualTimeSpent,
    tags = tags
)

fun ActivityItem.toFirestore(dueDate: Timestamp? = null) = FirestoreActivity(
    id = firestoreId,
    title = title,
    subjectId = subjectId,
    subjectName = subject,
    type = type,
    durationMin = durationMin,
    priority = priority.name,
    weightPercent = weightPercent,
    preparation = preparation,
    dueDate = dueDate,
    dueText = dueText,
    completed = completed,
    recommended = recommended,
    notes = notes,
    reminderEnabled = reminderEnabled,
    estimatedDifficulty = estimatedDifficulty,
    actualTimeSpent = actualTimeSpent,
    tags = tags
)

/** Intenta parsear textos como "05 / 09 / 2026" o "En 2 días" a Timestamp. */
fun parseDueDate(text: String): Timestamp? {
    // Formato dd / MM / yyyy
    val parts = text.replace(" ", "").split("/")
    if (parts.size == 3) {
        val day = parts[0].toIntOrNull() ?: return null
        val month = parts[1].toIntOrNull() ?: return null
        val year = parts[2].toIntOrNull() ?: return null
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day, 23, 59, 0)
        return Timestamp(cal.time)
    }
    // "En N días"
    val match = Regex("""En\s+(\d+)\s+días?""", RegexOption.IGNORE_CASE).find(text)
    if (match != null) {
        val days = match.groupValues[1].toIntOrNull() ?: return null
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, days)
        return Timestamp(cal.time)
    }
    if (text.contains("mañana", ignoreCase = true)) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 1)
        return Timestamp(cal.time)
    }
    return null
}
