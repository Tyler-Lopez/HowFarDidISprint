package com.company.howfardidisprint

// Singleton used to report tracking status from the Service to the Activity
object TrackingData {
    var isTracking: Boolean = false
    var timeTracked: Int = 0
}