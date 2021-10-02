package com.company.howfardidisprint

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.RunCircle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.HowFarDidISprintTheme
import com.company.howfardidisprint.ui.theme.roboto
import com.google.android.gms.location.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            HowFarDidISprintTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color(239, 240, 242)) {

                 //   var leaderBoards by rememberSaveable {
                 //   }
                    val context = LocalContext.current
                    val scoreEntryViewModel = ScoreEntryViewModel(this.application)
                    var leaderBoards by rememberSaveable {
                        mutableStateOf(scoreEntryViewModel.getData())
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
                    var running by rememberSaveable {
                        mutableStateOf(TrackingData.isTracking)
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
                            running = TrackingData.isTracking
                        } else {    // This would occur if we have reached over > 400 meters
                            if (distance >= 400) { // This check ensure we didn't abort for some other reason
                                scoreEntryViewModel.insertScore(
                                    ScoreEntry(
                                        time,
                                        dateToTimestamp(Calendar.getInstance().time) ?: 0L
                                    )
                                )
                                leaderBoards = scoreEntryViewModel.getData()
                            }
                            stopTracking()
                            running = false
                        }
                    }
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(title = {
                                Text(
                                    text = "SprintLogger",
                                    fontWeight = FontWeight.Bold
                                )
                            }, backgroundColor = Color.White,
                                navigationIcon = {
                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            Icons.Rounded.RunCircle,
                                            contentDescription = "",
                                            tint = Color(
                                                250, 82, 7
                                            )
                                        )
                                    }
                                })
                        },
                        content = {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 20.dp),
                                    elevation = 3.dp,
                                    shape = RectangleShape
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 20.dp)
                                    ) {
                                        SubHeader("400 METER SPRINT")
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 10.dp),
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Column(
                                                modifier = Modifier.weight(1f),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                FieldTitle(text = "Speed")
                                                FieldTitle(text = "Meters")
                                                FieldTitle(text = "Time")
                                            }
                                            Column(
                                                modifier = Modifier.weight(1f),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                FieldValue("%.2f".format(speed), "m/s")
                                                FieldValue(distance.toString(), "m")
                                                FieldValue(
                                                    if (time == -1) "0" else time.toString(),
                                                    "s"
                                                )
                                            }
                                        }
                                        OrangeButton(value = if (!running) "Start" else "Stop") {
                                            if (!TrackingData.isTracking) {
                                                TrackingData.trackingStartedOn =
                                                    System.currentTimeMillis()
                                                startService(LocationTrackingService.getIntent(this@MainActivity))
                                                TrackingData.isTracking = true
                                                running = true
                                                time = timeSinceStart()
                                            }
                                        }
                                        WhiteButton("View Sprint History") {
                                        }
                                        // This is a temporary debug tool to see if persisting data can work
                                        LazyColumn() {
                                            items(leaderBoards.size) {
                                                for (score in leaderBoards) {
                                                    Text(
                                                        text = " ${score.time} ${fromTimestamp(score.date)}",
                                                        fontSize = 15.sp,
                                                        fontFamily = roboto,
                                                        fontWeight = FontWeight.Normal,
                                                        textAlign = TextAlign.Center,
                                                        color = Color(40, 40, 40)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    )
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



