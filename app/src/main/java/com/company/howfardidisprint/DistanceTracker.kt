package com.company.howfardidisprint

import android.location.Location
import com.company.howfardidisprint.model.RunDistance

// Singleton used to report tracking status from the Service to the Activity
object DistanceTracker {
    var totalDistance = 0L
    var latestLocation: Location? = null
    var runDistance: RunDistance = RunDistance.MILE
    var startTime: Long? = null
    var endTime: Long? = null

    fun resetSingleton() {
        latestLocation = null
        totalDistance = 0L
        startTime = null
        endTime = null
    }

    fun timeSinceStart(): Int {
        return if (endTime != null) {
            (((endTime ?: 0L) - (startTime ?: 0L)) / 1000).toInt()
        } else {
            ((System.currentTimeMillis() - (startTime ?: System.currentTimeMillis())) / 1000).toInt()
        }
    }
}