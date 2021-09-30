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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.company.howfardidisprint.ui.theme.HowFarDidISprintTheme
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import java.net.URI.create

class MainActivity : ComponentActivity() {

    val FINE_LOCATION_RQ = 101
    val CAMERA_RQ = 102
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL: Long = 10 * 100;
    private val FASTEST_INTERVAL: Long = 500;
    private var lastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            HowFarDidISprintTheme {
                // A surface container using the 'background' color from the theme


                Surface(color = Color(31, 0, 171, 255)) {
                    var distance by remember {
                        mutableStateOf(DistanceTracker.totalDistance)
                    }
                    var running by remember {
                        mutableStateOf(false)
                    }
                    var time by remember {
                        mutableStateOf(60)
                    }
                    LaunchedEffect(key1 = DistanceTracker.totalDistance, key2 = time) {
                        distance = DistanceTracker.totalDistance
                        if(running) {
                            delay(1000L)
                            time--
                        }
                    }

                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val maxHeight = this.maxHeight
                        val dirtStart = this.maxHeight / 3


                        Canvas(modifier = Modifier.fillMaxSize()) {


                            for (i in 0..60) {
                                drawRect(
                                    color =
                                    if (i % 2 == 0)
                                        Color(101, 184, 67)
                                    else
                                        Color(42, 111, 23),
                                    size = Size(25.dp.toPx(), 40.dp.toPx()),
                                    topLeft = Offset(
                                        i * 25.dp.toPx(),
                                        maxHeight.toPx() - dirtStart.toPx() - 30.dp.toPx()
                                    )
                                )
                                for (j in 0..30) {
                                    drawRect(
                                        color =
                                        when (j % 2 == 0) {
                                            true -> {
                                                if (i % 2 == 0)
                                                    Color(192, 111, 29)
                                                else
                                                    Color(133, 67, 15)
                                            }
                                            false -> {
                                                if (i % 2 != 0)
                                                    Color(192, 111, 29)
                                                else
                                                    Color(133, 67, 15)
                                            }
                                        },
                                        size = Size(20.dp.toPx(), 20.dp.toPx()),
                                        topLeft = Offset(
                                            i * 20.dp.toPx(),
                                            maxHeight.toPx() - dirtStart.toPx() + j * 20.dp.toPx()
                                        )
                                    )
                                }

                            }
                        }
                    }
                    Column(
                        modifier = Modifier.offset(y = 200.dp).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "SCORE  ",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(230, 209, 27),
                            )
                            Text(
                                text = "${distance}",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(255, 255, 255),
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "TIME  ",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(230, 209, 27),
                            )
                            Text(
                                text = "$time",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(255, 255, 255),
                            )
                        }
                        Button(
                            modifier = Modifier.shadow(20.dp).height(100.dp).width(250.dp),
                            onClick = {
                                startLocationUpdates()
                                running = true
                                time--
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    185,
                                    52,
                                    53
                                )
                            )
                        ) {
                            Card(
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(20.dp),
                                backgroundColor = Color(2, 82, 155),
                                elevation = 10.dp
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (!running) "START!" else "GO FAST!",
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = Color(230, 209, 27),
                                    )
                                }
                            }
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
        if (Build.VERSION.SDK_INT >= 23 && checkPermission()) {
            LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
        }
    }

    private fun onLocationChanged(location: Location) {
        // create message for toast with updated latitude and longitude
        var msg = "Updated Location: " + location.latitude + " , " + location.longitude

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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true;
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf("Manifest.permission.ACCESS_FINE_LOCATION"),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION) {
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