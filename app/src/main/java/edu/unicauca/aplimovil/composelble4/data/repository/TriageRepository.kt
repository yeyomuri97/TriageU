package edu.unicauca.aplimovil.composelble4.data.repository

import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import edu.unicauca.aplimovil.composelble4.data.local.TriageDatabase
import edu.unicauca.aplimovil.composelble4.data.mapper.toEntity
import edu.unicauca.aplimovil.composelble4.data.mapper.toFirestore
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreActivity
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreAvailability
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreProgress
import edu.unicauca.aplimovil.composelble4.data.model.FirestoreSubject
import edu.unicauca.aplimovil.composelble4.data.model.UserProfile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TriageRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val localDb: TriageDatabase = TriageDatabase.getDatabase(
        FirebaseApp.getInstance().applicationContext
    )
) {

    private val userProfileDao = localDb.userProfileDao()
    private val subjectDao = localDb.subjectDao()
    private val activityDao = localDb.activityDao()
    private val availabilityDao = localDb.availabilityDao()
    private val progressDao = localDb.progressDao()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val userId: String?
        get() = auth.currentUser?.uid

    suspend fun signInAnonymously(): FirebaseUser {
        val result = auth.signInAnonymously().await()
        return result.user ?: error("No se pudo iniciar sesión anónima")
    }

    suspend fun ensureUser(): String {
        currentUser?.uid?.let {
            return it
        }

        return signInAnonymously().uid
    }

    private fun requireUid(): String {
        return userId
            ?: error("Usuario no autenticado. Llama a ensureUser() primero.")
    }

    private fun userDoc(uid: String) =
        db.collection("users").document(uid)

    private fun subjectsCol(uid: String) =
        userDoc(uid).collection("subjects")

    private fun activitiesCol(uid: String) =
        userDoc(uid).collection("activities")

    private fun availabilityCol(uid: String) =
        userDoc(uid).collection("availability")

    private fun progressDoc(uid: String) =
        userDoc(uid).collection("progress").document("summary")

    // ----------------------------------------------------
    // PERFIL
    // ----------------------------------------------------

    suspend fun saveUserProfile(profile: UserProfile) {
        val uid = ensureUser()

        val normalizedProfile = profile.copy(id = uid)

        // Guardar primero en Room
        userProfileDao.insert(
            normalizedProfile.toEntity(uid)
        )

        // Intentar sincronizar con Firebase
        try {
            userDoc(uid)
                .set(normalizedProfile, SetOptions.merge())
                .await()
        } catch (_: Exception) {
            // El dato permanece almacenado localmente en Room
        }
    }

    suspend fun getUserProfile(): UserProfile? {
        val uid = ensureUser()

        // Room es la primera fuente de lectura
        val localProfile = userProfileDao.getProfile(uid)

        if (localProfile != null) {
            return localProfile.toFirestore()
        }

        // Si no existe localmente, intentar obtenerlo de Firebase
        return try {
            val remoteProfile = userDoc(uid)
                .get()
                .await()
                .toObject(UserProfile::class.java)

            if (remoteProfile != null) {
                userProfileDao.insert(
                    remoteProfile.toEntity(uid)
                )
            }

            remoteProfile
        } catch (_: Exception) {
            null
        }
    }

    // ----------------------------------------------------
    // MATERIAS
    // ----------------------------------------------------

    fun observeSubjects(): Flow<List<FirestoreSubject>> = channelFlow {
        val uid = requireUid()

        // ROOM -> UI
        val localJob = launch {
            subjectDao.observeSubjects(uid).collect { localSubjects ->
                send(
                    localSubjects.map { it.toFirestore() }
                )
            }
        }

        // FIREBASE -> ROOM
        val registration = subjectsCol(uid)
            .orderBy("name")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                val remoteSubjects =
                    snapshot?.documents?.mapNotNull {
                        it.toObject(FirestoreSubject::class.java)
                    } ?: emptyList()

                launch {
                    subjectDao.insertAll(
                        remoteSubjects.map { it.toEntity(uid) }
                    )
                }
            }

        awaitClose {
            registration.remove()
            localJob.cancel()
        }
    }

    suspend fun addSubject(subject: FirestoreSubject): String {
        val uid = ensureUser()

        // Firestore puede generar el ID sin conexión
        val ref = subjectsCol(uid).document()

        val normalizedSubject = subject.copy(
            id = ref.id
        )

        // Primero Room
        subjectDao.insert(
            normalizedSubject.toEntity(uid)
        )

        // Luego Firebase
        try {
            ref.set(normalizedSubject).await()
        } catch (_: Exception) {
            // El dato continúa disponible localmente
        }

        return ref.id
    }

    suspend fun deleteSubject(subjectId: String) {
        val uid = ensureUser()

        // Primero local
        subjectDao.deleteByFirestoreId(
            uid = uid,
            firestoreId = subjectId
        )

        // Luego remoto
        try {
            subjectsCol(uid)
                .document(subjectId)
                .delete()
                .await()
        } catch (_: Exception) {
        }
    }

    // ----------------------------------------------------
    // ACTIVIDADES
    // ----------------------------------------------------

    fun observeActivities(): Flow<List<FirestoreActivity>> = channelFlow {
        val uid = requireUid()

        // ROOM -> UI
        val localJob = launch {
            activityDao.observeActivities(uid).collect { localActivities ->
                send(
                    localActivities.map { it.toFirestore() }
                )
            }
        }

        // FIREBASE -> ROOM
        val registration = activitiesCol(uid)
            .orderBy("dueDate", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                val remoteActivities =
                    snapshot?.documents?.mapNotNull {
                        it.toObject(FirestoreActivity::class.java)
                    } ?: emptyList()

                launch {
                    activityDao.insertAll(
                        remoteActivities.map { it.toEntity(uid) }
                    )
                }
            }

        awaitClose {
            registration.remove()
            localJob.cancel()
        }
    }

    suspend fun addActivity(
        activity: FirestoreActivity
    ): String {
        val uid = ensureUser()

        // Se genera de una vez un ID compatible con Firebase
        val ref = activitiesCol(uid).document()

        val normalizedActivity = activity.copy(
            id = ref.id
        )

        // Primero se almacena localmente
        activityDao.insert(
            normalizedActivity.toEntity(uid)
        )

        // Después se intenta sincronizar con Firebase
        try {
            ref.set(normalizedActivity).await()
        } catch (_: Exception) {
            // Room conserva la actividad
        }

        return ref.id
    }

    suspend fun updateActivity(
        activity: FirestoreActivity
    ) {
        val uid = ensureUser()

        require(activity.id.isNotBlank())

        // Room
        activityDao.insert(
            activity.toEntity(uid)
        )

        // Firebase
        try {
            activitiesCol(uid)
                .document(activity.id)
                .set(activity, SetOptions.merge())
                .await()
        } catch (_: Exception) {
        }
    }

    suspend fun toggleActivityCompleted(
        activityId: String,
        completed: Boolean
    ) {
        val uid = ensureUser()

        val completedAt =
            if (completed) System.currentTimeMillis() else null

        // Actualización local primero
        activityDao.updateCompleted(
            uid = uid,
            firestoreId = activityId,
            completed = completed,
            completedAt = completedAt
        )

        // Sincronización remota
        try {
            val data = mutableMapOf<String, Any>(
                "completed" to completed
            )

            if (completed) {
                data["completedAt"] = Timestamp.now()
            }

            activitiesCol(uid)
                .document(activityId)
                .update(data)
                .await()
        } catch (_: Exception) {
        }
    }

    suspend fun deleteActivity(activityId: String) {
        val uid = ensureUser()

        activityDao.deleteByFirestoreId(
            uid = uid,
            firestoreId = activityId
        )

        try {
            activitiesCol(uid)
                .document(activityId)
                .delete()
                .await()
        } catch (_: Exception) {
        }
    }

    // ----------------------------------------------------
    // DISPONIBILIDAD
    // ----------------------------------------------------

    fun observeAvailability():
            Flow<List<FirestoreAvailability>> = channelFlow {

        val uid = requireUid()

        // ROOM -> UI
        val localJob = launch {
            availabilityDao
                .observeAvailability(uid)
                .collect { localAvailability ->

                    send(
                        localAvailability.map {
                            it.toFirestore()
                        }
                    )
                }
        }

        // FIREBASE -> ROOM
        val registration =
            availabilityCol(uid)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        return@addSnapshotListener
                    }

                    val remoteAvailability =
                        snapshot?.documents?.mapNotNull {
                            it.toObject(
                                FirestoreAvailability::class.java
                            )
                        } ?: emptyList()

                    launch {
                        availabilityDao.insertAll(
                            remoteAvailability.map {
                                it.toEntity(uid)
                            }
                        )
                    }
                }

        awaitClose {
            registration.remove()
            localJob.cancel()
        }
    }

    suspend fun saveAvailability(
        items: List<FirestoreAvailability>
    ) {
        val uid = ensureUser()

        val normalizedItems = items.map { item ->

            val documentId =
                item.id.ifBlank {
                    item.day.lowercase()
                }

            item.copy(
                id = documentId
            )
        }

        // Guardar primero en Room
        availabilityDao.insertAll(
            normalizedItems.map {
                it.toEntity(uid)
            }
        )

        // Después intentar Firebase
        try {
            val batch = db.batch()

            normalizedItems.forEach { item ->

                batch.set(
                    availabilityCol(uid)
                        .document(item.id),
                    item
                )
            }

            batch.commit().await()

        } catch (_: Exception) {
        }
    }

    // ----------------------------------------------------
    // PROGRESO
    // ----------------------------------------------------

    fun observeProgress():
            Flow<FirestoreProgress?> = channelFlow {

        val uid = requireUid()

        // ROOM -> UI
        val localJob = launch {
            progressDao.observeProgress(uid)
                .collect { localProgress ->

                    send(
                        localProgress?.toFirestore()
                    )
                }
        }

        // FIREBASE -> ROOM
        val registration =
            progressDoc(uid)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        return@addSnapshotListener
                    }

                    val remoteProgress =
                        snapshot?.toObject(
                            FirestoreProgress::class.java
                        )

                    if (remoteProgress != null) {
                        launch {
                            progressDao.insert(
                                remoteProgress.toEntity(uid)
                            )
                        }
                    }
                }

        awaitClose {
            registration.remove()
            localJob.cancel()
        }
    }

    suspend fun saveProgress(
        progress: FirestoreProgress
    ) {
        val uid = ensureUser()

        // Primero Room
        progressDao.insert(
            progress.toEntity(uid)
        )

        // Después Firebase
        try {
            progressDoc(uid)
                .set(
                    progress,
                    SetOptions.merge()
                )
                .await()
        } catch (_: Exception) {
        }
    }
}