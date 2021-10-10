package com.company.howfardidisprint.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://www.youtube.com/watch?v=vsDkhRTMdA0 @4:47
@Database(entities = [Run::class], version = 2, exportSchema = false)
abstract class RunDatabase : RoomDatabase() {

    // Abstract because Room generates the implementation
    abstract fun runDao(): RunDao

    companion object {
        @Volatile
        private var INSTANCE: RunDatabase? = null

        fun getDatabase(context: Context): RunDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RunDatabase::class.java,
                        "score_list_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
