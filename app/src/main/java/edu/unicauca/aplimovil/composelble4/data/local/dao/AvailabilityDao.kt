package edu.unicauca.aplimovil.composelble4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.composelble4.data.local.entity.AvailabilityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AvailabilityDao {

    @Query(
        "SELECT * FROM availability " +
                "WHERE uid = :uid ORDER BY id ASC"
    )
    fun observeAvailability(uid: String): Flow<List<AvailabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AvailabilityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<AvailabilityEntity>)

    @Query(
        "DELETE FROM availability " +
                "WHERE uid = :uid AND day = :day"
    )
    suspend fun deleteByDay(
        uid: String,
        day: String
    )

    @Query("DELETE FROM availability WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}