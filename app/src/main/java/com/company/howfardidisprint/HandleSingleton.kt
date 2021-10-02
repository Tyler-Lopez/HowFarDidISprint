package com.company.howfardidisprint

import androidx.compose.foundation.text.selection.DisableSelection

fun refreshSingleton() {
    TrackingData.lastLocation = null
    TrackingData.timeTracked = 0
    TrackingData.isTracking = false
    DistanceTracker.latestSpeed = 0f
    DistanceTracker.startLocation = null
    DistanceTracker.latestLocation = null
    DistanceTracker.totalDistance = 0L
}

fun timeSinceStart() = ((System.currentTimeMillis() - TrackingData.trackingStartedOn)/1000).toInt()