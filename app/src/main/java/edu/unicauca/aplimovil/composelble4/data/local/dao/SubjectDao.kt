package edu.unicauca.aplimovil.composelble4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.composelble4.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Query("SELECT * FROM subjects WHERE uid = :uid ORDER BY name ASC")
    fun observeSubjects(uid: String): Flow<List<SubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: SubjectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subjects: List<SubjectEntity>)

    @Query(
        "DELETE FROM subjects " +
                "WHERE uid = :uid AND firestoreId = :firestoreId"
    )
    suspend fun deleteByFirestoreId(
        uid: String,
        firestoreId: String
    )

    @Query("DELETE FROM subjects WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}