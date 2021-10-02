package com.company.howfardidisprint

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://www.youtube.com/watch?v=vsDkhRTMdA0 @4:47
@Database(entities = [ScoreEntry::class], version = 1, exportSchema = false)
abstract class ScoreEntryRoomDatabase : RoomDatabase() {

    abstract fun ScoreEntryDao(): ScoreEntryDao

    companion object {
        @Volatile
        private var INSTANCE: ScoreEntryRoomDatabase? = null

        fun getDatabase(context: Context): ScoreEntryRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ScoreEntryRoomDatabase::class.java,
                        "score_list_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
