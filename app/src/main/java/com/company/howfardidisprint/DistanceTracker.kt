package com.company.howfardidisprint

import android.location.Location

// Singleton used to report tracking status from the Service to the Activity
object DistanceTracker {
    // Why private val and getter/setter?
    // Essential for thread safe singleton https://codereview.stackexchange.com/questions/214313/destroy-singleton-pattern-in-kotlin
    private val latestLocation: MutableList<Location> = mutableListOf()
    private val totalDistance: MutableList<Long> = mutableListOf()
    private val startTime: MutableList<Long> = mutableListOf()
    private val endTime: MutableList<Long> = mutableListOf()

    // Getters
    fun getLocation(): Location? = if (latestLocation.isEmpty()) null else latestLocation.first()
    fun getTotalDistance(): Long = if (totalDistance.isEmpty()) 0L else totalDistance.first()
    fun getStartTime(): Long? = if (startTime.isEmpty()) null else startTime.first()
    fun getEndTime(): Long? = if (endTime.isEmpty()) null else startTime.first()

    // Setters
    fun setLocation(location: Location) {
        latestLocation.apply {
            this.clear()
            this.add(0, location)
        }
    }
    fun setDistance(distanceIncrease: Long) {
        totalDistance.apply {
            val previousDistance = getTotalDistance()
            this.clear()
            this.add(0, previousDistance + distanceIncrease)
        }
    }
    fun setTime() {
        startTime.apply {
            this.clear()
            this.add(0, System.currentTimeMillis())
        }
    }
    fun setEndTime() {
        endTime.apply {
            this.clear()
            this.add(0, System.currentTimeMillis())
        }
    }


    fun resetSingleton() {
        latestLocation.clear()
        totalDistance.clear()
        startTime.clear()
        endTime.clear()
    }

    fun timeSinceStart(): Int {
        return if (getEndTime() != null) {
            (((getEndTime()!!) - (getStartTime()!!)) / 1000).toInt()
        } else {
            ((System.currentTimeMillis() - (getStartTime() ?: System.currentTimeMillis())) / 1000).toInt()
        }
    }
}