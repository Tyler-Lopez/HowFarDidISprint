package com.company.howfardidisprint.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.howfardidisprint.model.Run

@Dao
interface RunDao {
    @Query("SELECT * FROM run_table ORDER BY start DESC")
    fun getAllRunsByDate(): LiveData<List<Run>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(run: Run)

    @Query("DELETE FROM run_table")
    suspend fun deleteAll()
}