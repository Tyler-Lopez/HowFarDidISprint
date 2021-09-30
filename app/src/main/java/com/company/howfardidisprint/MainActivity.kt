package com.company.howfardidisprint

import DistanceTracker
import GpsTrackerService
import android.Manifest
import android.app.TaskStackBuilder.create
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.company.howfardidisprint.ui.theme.HowFarDidISprintTheme
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import java.net.URI.create

class MainActivity : ComponentActivity() {

    val FINE_LOCATION_RQ = 101
    val CAMERA_RQ = 102
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL:Long = 10 * 100;
    private val FASTEST_INTERVAL:Long = 500;
    private var lastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            HowFarDidISprintTheme {
                // A surface container using the 'background' color from the theme

                DistanceTracker.totalDistance = 0L

                Surface(color = MaterialTheme.colors.background) {
                    var distance by remember {
                        mutableStateOf(DistanceTracker.totalDistance)
                   }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Card(modifier = Modifier.height(200.dp)) {
                           Text("${distance}")
                        }
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        }) {
                            Text("permissions")
                        }
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            distance = DistanceTracker.totalDistance
                        }) {
                            Text("update")
                        }
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            startLocationUpdates()
                        }) {
                            Text("start")
                        }
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                     //       GpsTrackerService.stopTracking(this@MainActivity)
                        }) {
                            Text("stop")
                        }
                    }


                }
            }
        }
    }
    protected fun startLocationUpdates() {
        // initialize location request object
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.run {
            setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            setInterval(UPDATE_INTERVAL)
            setFastestInterval(FASTEST_INTERVAL)
        }

        // initialize locationo setting request builder object
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        // initialize location service object
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient!!.checkLocationSettings(locationSettingsRequest)

        // call register location listner
        registerLocationListner()


    }
    private fun registerLocationListner() {
        // initialize location callback object
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                onLocationChanged(locationResult!!.getLastLocation())
            }
        }
        // add permission if android version is greater then 23
        if(Build.VERSION.SDK_INT >= 23 && checkPermission()) {
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
        }
    }
    private fun onLocationChanged(location: Location) {
        // create message for toast with updated latitude and longitude
        var msg = "Updated Location: " + location.latitude  + " , " +location.longitude

        if (lastLocation == null) {
            lastLocation = location
        }

        location.let { its_last ->

            val distanceInMeters = its_last.distanceTo(lastLocation)

            DistanceTracker.totalDistance += distanceInMeters.toLong()

            println("TRACKER" + "Completed: ${DistanceTracker.totalDistance} meters, (added $distanceInMeters)")
        }
        lastLocation = location

        // show toast message with updated location
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()

    }
    private fun checkPermission() : Boolean {
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf("Manifest.permission.ACCESS_FINE_LOCATION"),1)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION ) {
                registerLocationListner()
            }
        }
    }

}




@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HowFarDidISprintTheme {
        Greeting("Android")
    }
}