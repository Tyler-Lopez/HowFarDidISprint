package com.company.howfardidisprint

import android.location.Location
import com.company.howfardidisprint.model.RunDistance

// Singleton used to report tracking status from the Service to the Activity
object DistanceTracker {
    // Why private val and getter/setter?
    // Essential for thread safe singleton https://codereview.stackexchange.com/questions/214313/destroy-singleton-pattern-in-kotlin
    var totalDistance = 0L
    var latestLocation: Location? = null
    var runDistance: RunDistance = RunDistance.MILE
    private val startTime: MutableList<Long> = mutableListOf()
    fun getTime(): Long? {
        return if (startTime.isEmpty()) {
            null
        } else {
            startTime.first()
        }
    }
    fun setTime() {
        startTime.add(0, System.currentTimeMillis())
    }

    var endTime: Long? = null

    fun resetSingleton() {
        latestLocation = null
        totalDistance = 0L
        endTime = null
    }

    fun timeSinceStart(): Int {
        return if (endTime != null) {
            (((endTime ?: 0L) - (getTime() ?: 0L)) / 1000).toInt()
        } else {
            ((System.currentTimeMillis() - (getTime() ?: System.currentTimeMillis())) / 1000).toInt()
        }
    }
}