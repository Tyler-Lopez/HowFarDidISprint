package com.company.howfardidisprint.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity represents a table within the database
@Entity(tableName = "run_table")
data class Run(
    @PrimaryKey
    @ColumnInfo(name = "start")
    val startTime: Long, // Local time user started run
    @PrimaryKey
    @ColumnInfo(name = "time")
    val totalTime: Long, // Seconds
    @PrimaryKey
    @ColumnInfo(name = "distance")
    val distance: RunDistance //
)