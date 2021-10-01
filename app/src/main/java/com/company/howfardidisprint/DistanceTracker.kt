package com.company.howfardidisprint

import android.location.Location

// Singleton used to report tracking status from the Service to the Activity
object DistanceTracker {
    var totalDistance = 0L
    var startLocation: Location? = null
    var latestLocation: Location? = null
    var latestSpeed: Float = 0f
}