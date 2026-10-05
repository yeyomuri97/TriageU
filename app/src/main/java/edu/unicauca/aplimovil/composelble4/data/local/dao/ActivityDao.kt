package edu.unicauca.aplimovil.composelble4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.unicauca.aplimovil.composelble4.data.local.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query(
        "SELECT * FROM activities " +
                "WHERE uid = :uid " +
                "ORDER BY dueDate ASC"
    )
    fun observeActivities(uid: String): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ActivityEntity?

    @Query(
        "SELECT * FROM activities " +
                "WHERE uid = :uid AND firestoreId = :firestoreId LIMIT 1"
    )
    suspend fun getByFirestoreId(
        uid: String,
        firestoreId: String
    ): ActivityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(activities: List<ActivityEntity>)

    @Update
    suspend fun update(activity: ActivityEntity)

    @Query(
        "UPDATE activities " +
                "SET completed = :completed, completedAt = :completedAt " +
                "WHERE uid = :uid AND firestoreId = :firestoreId"
    )
    suspend fun updateCompleted(
        uid: String,
        firestoreId: String,
        completed: Boolean,
        completedAt: Long?
    )

    @Query("DELETE FROM activities WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query(
        "DELETE FROM activities " +
                "WHERE uid = :uid AND firestoreId = :firestoreId"
    )
    suspend fun deleteByFirestoreId(
        uid: String,
        firestoreId: String
    )

    @Query("DELETE FROM activities WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}