package com.company.howfardidisprint

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*

class LocationTrackingService : Service() {

    private var startId = 0
    private var lastLocation: Location? = null

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedClient: FusedLocationProviderClient
    private val actionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.extras?.getString(ACTION_NAME)) {
                ACTION_STOP_TRACKING -> stopTrackingService()
            }
        }
    }

    companion object {

        private const val GPS_ACTION = "GPS_ACTION"
        private const val ACTION_NAME = "ACTION_NAME"

        const val ACTION_STOP_TRACKING = "ACTION_STOP_TRACKING"

        fun getIntent(context: Context) = Intent(context, LocationTrackingService::class.java)

        fun stopTracking(context: Context) =
            context.sendBroadcast(Intent(GPS_ACTION).apply { putExtra(ACTION_NAME, ACTION_STOP_TRACKING) })
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.startId = startId
        startForeground(startId, getNotification())
        startLocationUpdates()
        registerReceiver(actionReceiver, IntentFilter(GPS_ACTION))
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopTrackingService(){
        stopForeground(true)
        stopLocationTracking()
        stopSelf(startId)
    }

    override fun onDestroy() {
        kotlin.runCatching { unregisterReceiver(actionReceiver) }
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        // initialize location request object
        // Reset distance and last location location
        DistanceTracker.totalDistance = 0L

        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 2000
            smallestDisplacement = 15.0F
        }

        locationCallback = object: LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                result?.let {
                    // If lastLocation is null, set it equal to most recent location then return
                    if (lastLocation == null) {
                        lastLocation = it.lastLocation
                        return@let
                    }
                    it.lastLocation?.let { its_last ->
                        val distanceInMeters = its_last.distanceTo(lastLocation)
                        DistanceTracker.latestLocation = it.lastLocation
                        DistanceTracker.totalDistance += distanceInMeters.toLong()
                        // Are we over the total distance for the selected run?
                        if (DistanceTracker.totalDistance >= DistanceTracker.runDistance.distance) {
                            DistanceTracker.endTime = System.currentTimeMillis()
                            lastLocation = its_last
                            stopLocationTracking()
                        }
                        lastLocation = its_last
                    }
                }
                super.onLocationResult(result)
            }
        }
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        fusedClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationTracking(){
        fusedClient.removeLocationUpdates(locationCallback)
    }
    private fun getNotification(): Notification? {

        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("gps_tracker", "GPS Tracker")
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            ""
        }

        val b = NotificationCompat.Builder(this, channelId)

        b.setOngoing(true)
            .setContentTitle("Currently tracking GPS location...")
        // .setSmallIcon(R.mipmap.ic_launcher)

        return b.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}