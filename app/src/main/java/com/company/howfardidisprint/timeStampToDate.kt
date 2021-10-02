package com.company.howfardidisprint

import java.util.*

fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
}

fun dateToTimestamp(date: Date?): Long? {
    return date?.time
}