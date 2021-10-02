package com.company.howfardidisprint

import android.os.Build
import androidx.annotation.RequiresApi
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

fun hasRunToday(scores: List<ScoreEntry>): Boolean {
    if (scores.isNullOrEmpty()) return false
    val score = scores.first()
    val dateOfRun = millisecondsToLocalDateTime(score.date)
    val day = dateOfRun.dayOfMonth
    val today = LocalDateTime.now().dayOfMonth
    println("HERE $dateOfRun // $day // $today")
    return day == today
}

/*
LazyColumn() {
                                                items(leaderBoards.size) {
                                                    val score = leaderBoards[it]
                                                    val dateOfRun = millisecondsToLocalDateTime(score.date)
                                                    val day = dateOfRun.dayOfMonth
                                                    val month = dateOfRun.monthValue
                                                    val year = dateOfRun.year
                                                    Text(
                                                        text = " ${score.time} $day/$month/$year $test",
                                                        fontSize = 15.sp,
                                                        fontFamily = roboto,
                                                        fontWeight = FontWeight.Normal,
                                                        textAlign = TextAlign.Center,
                                                        color = Color(40, 40, 40)
                                                    )
                                                    test++
                                                }
                                        }*/