package com.company.howfardidisprint

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object
// https://www.youtube.com/watch?v=vsDkhRTMdA0
// https://www.youtube.com/watch?v=lwAvI3WDXBY @ 6:30
@Dao
interface ScoreEntryDao {
    @Query("SELECT * FROM score_list ORDER BY time ASC")
    fun getScores(): LiveData<List<ScoreEntry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(score: ScoreEntry)

//    @Query("DELETE FROM score_list")
  //  suspend fun deleteAll()
}