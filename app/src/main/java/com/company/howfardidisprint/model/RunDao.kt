package com.company.howfardidisprint.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RunDao {
    @Query("SELECT * FROM run_table ORDER BY start DESC")
    fun getAllRunsByDate(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table WHERE distance = :runDistance ORDER BY time ASC")
    fun getRunsOfDistanceBySpeed(runDistance: RunDistance): LiveData<List<Run>>

    @Query("SELECT * FROM run_table WHERE distance = :runDistance ORDER BY start DESC")
    fun getRunsOfDistanceByDate(runDistance: RunDistance): LiveData<List<Run>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(run: Run)

    @Query("DELETE FROM run_table")
    suspend fun deleteAll()
}