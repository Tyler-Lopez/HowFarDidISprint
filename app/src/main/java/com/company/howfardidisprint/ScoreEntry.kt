package com.company.howfardidisprint

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "score_list")
data class ScoreEntry(
    @PrimaryKey
    @ColumnInfo(name = "time")
    val time: Int,

    @ColumnInfo(name = "date")
    val date: Long
)