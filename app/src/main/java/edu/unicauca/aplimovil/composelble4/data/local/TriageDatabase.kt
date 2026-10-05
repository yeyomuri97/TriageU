package edu.unicauca.aplimovil.composelble4.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.unicauca.aplimovil.composelble4.data.local.dao.ActivityDao
import edu.unicauca.aplimovil.composelble4.data.local.dao.AvailabilityDao
import edu.unicauca.aplimovil.composelble4.data.local.dao.ProgressDao
import edu.unicauca.aplimovil.composelble4.data.local.dao.SubjectDao
import edu.unicauca.aplimovil.composelble4.data.local.dao.UserProfileDao
import edu.unicauca.aplimovil.composelble4.data.local.entity.ActivityEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.AvailabilityEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.ProgressEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.SubjectEntity
import edu.unicauca.aplimovil.composelble4.data.local.entity.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        SubjectEntity::class,
        ActivityEntity::class,
        AvailabilityEntity::class,
        ProgressEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TriageDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao

    abstract fun subjectDao(): SubjectDao

    abstract fun activityDao(): ActivityDao

    abstract fun availabilityDao(): AvailabilityDao

    abstract fun progressDao(): ProgressDao

    companion object {

        @Volatile
        private var INSTANCE: TriageDatabase? = null

        fun getDatabase(context: Context): TriageDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TriageDatabase::class.java,
                    "triageu_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}