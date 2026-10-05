package edu.unicauca.aplimovil.composelble4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.composelble4.data.local.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Query("SELECT * FROM progress WHERE uid = :uid LIMIT 1")
    fun observeProgress(uid: String): Flow<ProgressEntity?>

    @Query("SELECT * FROM progress WHERE uid = :uid LIMIT 1")
    suspend fun getProgress(uid: String): ProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: ProgressEntity): Long

    @Query("DELETE FROM progress WHERE uid = :uid")
    suspend fun deleteByUid(uid: String)
}