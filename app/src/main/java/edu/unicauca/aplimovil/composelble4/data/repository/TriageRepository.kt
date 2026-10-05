package edu.unicauca.aplimovil.composelble4.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreActivity
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreAvailability
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreProgress
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreSubject
import edu.unicauca.aplimovil.composelble4.data.model.UserProfile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TriageRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    val currentUser: FirebaseUser? get() = auth.currentUser
    val userId: String? get() = auth.currentUser?.uid

    suspend fun signInAnonymously(): FirebaseUser {
        val result = auth.signInAnonymously().await()
        return result.user ?: error("No se pudo iniciar sesión anónima")
    }

    suspend fun ensureUser(): String {
        currentUser?.uid?.let { return it }
        return signInAnonymously().uid
    }

    private fun requireUid(): String =
        userId ?: error("Usuario no autenticado. Llama a ensureUser() primero.")

    private fun userDoc(uid: String) = db.collection("users").document(uid)
    private fun subjectsCol(uid: String) = userDoc(uid).collection("subjects")
    private fun activitiesCol(uid: String) = userDoc(uid).collection("activities")
    private fun availabilityCol(uid: String) = userDoc(uid).collection("availability")
    private fun progressDoc(uid: String) = userDoc(uid).collection("progress").document("summary")

    // ---- Perfil ----
    suspend fun saveUserProfile(profile: UserProfile) {
        val uid = ensureUser()
        userDoc(uid).set(profile.copy(id = uid), SetOptions.merge()).await()
    }

    suspend fun getUserProfile(): UserProfile? {
        val uid = ensureUser()
        return userDoc(uid).get().await().toObject(UserProfile::class.java)
    }

    // ---- Materias ----
    fun observeSubjects(): Flow<List<FirestoreSubject>> = callbackFlow {
        val uid = requireUid()
        val reg = subjectsCol(uid).orderBy("name")
            .addSnapshotListener { snap, err ->
                if (err != null) { close(err); return@addSnapshotListener }
                trySend(snap?.documents?.mapNotNull { it.toObject(FirestoreSubject::class.java) } ?: emptyList())
            }
        awaitClose { reg.remove() }
    }

    suspend fun addSubject(subject: FirestoreSubject): String {
        val uid = ensureUser()
        val ref = subjectsCol(uid).document()
        ref.set(subject.copy(id = ref.id)).await()
        return ref.id
    }

    suspend fun deleteSubject(subjectId: String) {
        val uid = ensureUser()
        subjectsCol(uid).document(subjectId).delete().await()
    }

    // ---- Actividades ----
    fun observeActivities(): Flow<List<FirestoreActivity>> = callbackFlow {
        val uid = requireUid()
        val reg = activitiesCol(uid)
            .orderBy("dueDate", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) { close(err); return@addSnapshotListener }
                trySend(snap?.documents?.mapNotNull { it.toObject(FirestoreActivity::class.java) } ?: emptyList())
            }
        awaitClose { reg.remove() }
    }

    suspend fun addActivity(activity: FirestoreActivity): String {
        val uid = ensureUser()
        val ref = activitiesCol(uid).document()
        ref.set(activity.copy(id = ref.id)).await()
        return ref.id
    }

    suspend fun updateActivity(activity: FirestoreActivity) {
        val uid = ensureUser()
        require(activity.id.isNotBlank())
        activitiesCol(uid).document(activity.id).set(activity, SetOptions.merge()).await()
    }

    suspend fun toggleActivityCompleted(activityId: String, completed: Boolean) {
        val uid = ensureUser()
        val data = mutableMapOf<String, Any>("completed" to completed)
        if (completed) data["completedAt"] = Timestamp.now()
        activitiesCol(uid).document(activityId).update(data as Map<String, Any>).await()
    }

    suspend fun deleteActivity(activityId: String) {
        val uid = ensureUser()
        activitiesCol(uid).document(activityId).delete().await()
    }

    // ---- Disponibilidad ----
    fun observeAvailability(): Flow<List<FirestoreAvailability>> = callbackFlow {
        val uid = requireUid()
        val reg = availabilityCol(uid).addSnapshotListener { snap, err ->
            if (err != null) { close(err); return@addSnapshotListener }
            trySend(snap?.documents?.mapNotNull { it.toObject(FirestoreAvailability::class.java) } ?: emptyList())
        }
        awaitClose { reg.remove() }
    }

    suspend fun saveAvailability(items: List<FirestoreAvailability>) {
        val uid = ensureUser()
        val batch = db.batch()
        items.forEach { item ->
            val docId = item.id.ifBlank { item.day.lowercase() }
            batch.set(availabilityCol(uid).document(docId), item.copy(id = docId))
        }
        batch.commit().await()
    }

    // ---- Progreso ----
    fun observeProgress(): Flow<FirestoreProgress?> = callbackFlow {
        val uid = requireUid()
        val reg = progressDoc(uid).addSnapshotListener { snap, err ->
            if (err != null) { close(err); return@addSnapshotListener }
            trySend(snap?.toObject(FirestoreProgress::class.java))
        }
        awaitClose { reg.remove() }
    }

    suspend fun saveProgress(progress: FirestoreProgress) {
        val uid = ensureUser()
        progressDoc(uid).set(progress, SetOptions.merge()).await()
    }
}
