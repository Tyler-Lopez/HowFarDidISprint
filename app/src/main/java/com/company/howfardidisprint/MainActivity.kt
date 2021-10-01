package com.company.howfardidisprint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
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
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            HowFarDidISprintTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color(31, 0, 171, 255)) {
                    var leaderBoards by rememberSaveable {
                        mutableStateOf(mutableListOf<ScoreEntry>())
                    }
                    var distance by rememberSaveable {
                        mutableStateOf(DistanceTracker.totalDistance)
                    }
                    var running by rememberSaveable {
                        mutableStateOf(TrackingData.isTracking)
                    }
                    var time by rememberSaveable {
                        mutableStateOf(TrackingData.timeTracked)
                    }
                    var speed by rememberSaveable {
                        mutableStateOf(DistanceTracker.latestSpeed)
                    }
                    LaunchedEffect(key1 = time) {
                        if (running && time != 0) {
                            running = TrackingData.isTracking
                            speed = DistanceTracker.latestSpeed
                            distance = DistanceTracker.totalDistance
                            delay(1000L)
                            TrackingData.timeTracked++
                            time = TrackingData.timeTracked
                        } else if (time > 0) {
                            leaderBoards.add(ScoreEntry(time)) // Add the amount of meters to leaderboard
                            LocationTrackingService.stopTracking(this@MainActivity) // Stop tracking the location!
                            stopService(LocationTrackingService.getIntent(this@MainActivity))
                        } else if (time != 0) {
                            running = TrackingData.isTracking
                            delay(1000L)
                            TrackingData.timeTracked++
                            time = TrackingData.timeTracked
                        }
                    }

                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val maxHeight = this.maxHeight
                        val dirtStart = this.maxHeight / 3
                        // Canvas containing artwork for backdrop
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
                                drawRect(
                                    color =
                                    if (i % 2 == 0)
                                        Color(101, 184, 67)
                                    else
                                        Color(42, 111, 23),
                                    size = Size(5.dp.toPx(), 50.dp.toPx()),
                                    topLeft = Offset(
                                        (i * 25).dp.toPx() - 25.dp.toPx(),
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

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "SPEED  ",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = Color(230, 209, 27),
                                )
                                Text(
                                    text = "%.2f".format(speed),
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = Color(255, 255, 255),
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "METERS  ",
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(bottom = 10.dp),
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
                                modifier = Modifier
                                    .shadow(20.dp)
                                    .height(100.dp)
                                    .width(250.dp),
                                onClick = {
                                    if (!running) {
                                        startService(LocationTrackingService.getIntent(this@MainActivity))
                                        TrackingData.isTracking = true
                                        TrackingData.timeTracked = 0
                                        running = TrackingData.isTracking
                                        time = -1
                                    }
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(bottom = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    for (score in leaderBoards) {
                                        Card(modifier = Modifier.width(200.dp).padding(top = 5.dp)) {
                                            Text(
                                                text = "${score.meters}",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                color = Color.DarkGray,
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    // If the app is paused, stop the service and reset
    override fun onPause() {
        // https://stackoverflow.com/questions/10971284/detect-activity-being-paused-due-to-configuration-change
        if (!isChangingConfigurations) {
            DistanceTracker.totalDistance = 0L
            TrackingData.isTracking = false
            TrackingData.timeTracked = 0
            LocationTrackingService.stopTracking(this@MainActivity) // Stop tracking the location!
            stopService(LocationTrackingService.getIntent(this@MainActivity))
        }
        super.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            DistanceTracker.totalDistance = 0L
            TrackingData.isTracking = false
            TrackingData.timeTracked = 0
            LocationTrackingService.stopTracking(this@MainActivity) // Stop tracking the location!
            stopService(LocationTrackingService.getIntent(this@MainActivity))
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