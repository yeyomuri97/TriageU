package edu.unicauca.aplimovil.composelble4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.composelble4.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profile WHERE uid = :uid LIMIT 1")
    fun observeProfile(uid: String): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE uid = :uid LIMIT 1")
    suspend fun getProfile(uid: String): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: UserProfileEntity)

    @Query("DELETE FROM user_profile WHERE uid = :uid")
    suspend fun deleteByUid(uid: String)
}