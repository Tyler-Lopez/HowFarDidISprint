package com.company.howfardidisprint

import android.os.Build
import androidx.annotation.RequiresApi
import com.company.howfardidisprint.model.Run
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
}

fun dateToTimestamp(date: Date?): Long? {
    return date?.time
}

fun convertToLocalDateTimeViaMillisecond(dateToConvert: Date): LocalDateTime =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

fun millisecondsToLocalDateTime(milliseconds: Long): LocalDateTime {
    return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
}

fun hasRunToday(run: List<Run>): Boolean {
    if (run.isEmpty())
        return false

    val dateOfRun = millisecondsToLocalDateTime(run.first().startTime)
    val day = dateOfRun.dayOfMonth
    val today = LocalDateTime.now().dayOfMonth
    println("HERE $dateOfRun // $day // $today")
    return day == today
}
