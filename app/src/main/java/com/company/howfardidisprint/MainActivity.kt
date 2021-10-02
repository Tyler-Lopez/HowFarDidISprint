package com.company.howfardidisprint

import android.os.Bundle
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
import com.company.howfardidisprint.ui.theme.roboto
import com.google.android.gms.location.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            HowFarDidISprintTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color(239, 240, 242)) {
                    var leaderBoards by rememberSaveable {
                        mutableStateOf(mutableListOf<ScoreEntry>())
                    }
                    var distance by rememberSaveable {
                        mutableStateOf(DistanceTracker.totalDistance)
                    }
                    var time by rememberSaveable {
                        mutableStateOf(-1)
                    }
                    var speed by rememberSaveable {
                        mutableStateOf(DistanceTracker.latestSpeed)
                    }
                    // This coroutine listens for changes in a key, everytime time is changed invoke the corutine again
                    LaunchedEffect(key1 = time) {
                        // If the running flag is still true
                        if (TrackingData.isTracking) {
                            speed =
                                DistanceTracker.latestSpeed // Update speed variable with latest speed
                            distance =
                                DistanceTracker.totalDistance // Update distance with latest speed
                            delay(1000L) // Wait one second
                            time = timeSinceStart()
                        } else {
                            // This would occur if we have reached over > 400 meters
                            stopTracking()
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            elevation = 3.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 1.dp)
                                            .background(Color(240, 241, 243))
                                            .border(1.dp, Color(229, 224, 221))
                                            .padding(10.dp)
                                            .height(60.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = "Speed ",
                                            fontSize = 40.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(0, 0, 0),
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 1.dp)
                                            .background(Color(240, 241, 243))
                                            .border(1.dp, Color(229, 224, 221))
                                            .padding(10.dp)
                                            .height(60.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = "Meters ",
                                            fontSize = 40.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(0, 0, 0),
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 1.dp)
                                            .background(Color(240, 241, 243))
                                            .border(1.dp, Color(229, 224, 221))
                                            .padding(10.dp)
                                            .height(60.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = "Time ",
                                            fontSize = 40.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(0, 0, 0),
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .height(60.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = "%.2f".format(speed),
                                            fontSize = 30.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(20, 20, 20),
                                        )
                                        Text(
                                            text = " m/s",
                                            fontSize = 15.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(40, 40, 40),
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .height(60.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = distance.toString(),
                                            fontSize = 30.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(20, 20, 20),
                                        )
                                        Text(
                                            text = " m",
                                            fontSize = 15.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(40, 40, 40),
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .height(60.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = if (time == -1) "0" else time.toString(),
                                            fontSize = 30.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(20, 20, 20),
                                        )
                                        Text(
                                            text = " s",
                                            fontSize = 15.sp,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = Color(40, 40, 40),
                                        )
                                    }
                                }
                            }
                        }

                        Button(
                            modifier = Modifier
                                .height(100.dp)
                                .width(350.dp)
                                .padding(vertical = 10.dp),
                            onClick = {
                                if (!TrackingData.isTracking) {
                                    TrackingData.trackingStartedOn = System.currentTimeMillis()
                                    startService(LocationTrackingService.getIntent(this@MainActivity))
                                    TrackingData.isTracking = true
                                    time = timeSinceStart()
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    250, 82, 7
                                )
                            )
                        ) {

                                Text(
                                    text = if (!TrackingData.isTracking) "Start" else "Stop",
                                    fontSize = 30.sp,
                                    fontFamily = roboto,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    color = Color(255, 255, 255),
                                )
                        }


                        Button(

                            onClick = {
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .height(100.dp)
                                .width(350.dp)
                           //     .border(2.dp, Color(250, 82, 7), shape = RoundedCornerShape(10.dp))
                                .padding(vertical = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    255, 255, 255
                                )
                            )
                        ) {
                                Text(
                                    text = "View Sprint History",
                                    fontSize = 30.sp,
                                    fontFamily = roboto,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    color = Color(250, 82, 7),
                                )
                        }
                    }
                }
            }
        }
    }

    // Is it necessary to call all three here?
    override fun onPause() {
        // https://stackoverflow.com/questions/10971284/detect-activity-being-paused-due-to-configuration-change
        if (!isChangingConfigurations)
            stopTracking()
        super.onPause()
    }

    override fun onStop() {
        if (!isChangingConfigurations)
            stopTracking()
        super.onStop()
    }

    override fun onDestroy() {
        if (!isChangingConfigurations)
            stopTracking()
        super.onDestroy()
    }

    fun stopTracking() {
        LocationTrackingService.stopTracking(this@MainActivity) // Stop tracking the location!
        stopService(LocationTrackingService.getIntent(this@MainActivity))
        refreshSingleton()
    }
}

