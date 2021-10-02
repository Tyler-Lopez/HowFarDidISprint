package com.company.howfardidisprint

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.roboto
import kotlinx.coroutines.delay
import java.util.*


@Composable
fun SprintScreen(
    navController: NavController,
    startTracking: () -> Unit,
    stopTracking: () -> Unit,
) {
    val context = LocalContext.current
    val mScoreEntryViewModel: ScoreEntryViewModel = viewModel(
        factory = ScoreEntryViewModelFactory(context.applicationContext as Application)
    )
    var leaderBoards =
        mScoreEntryViewModel.readAllData.observeAsState(listOf()).value

    // This is kind of a hacky, bad solution to ensuring this is updated after the user runs
    var hasRunToday by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = leaderBoards.size) {
        hasRunToday = hasRunToday(leaderBoards)
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
        } else {
            distance =
                DistanceTracker.totalDistance // This would occur if we have reached over > 400 meters

            if (distance >= 400) {
                mScoreEntryViewModel.insertScore(
                    ScoreEntry(
                        time,
                        dateToTimestamp(Calendar.getInstance().time) ?: 0L
                    )
                )
            }
            stopTracking() // Push information up to main activity to close this tracking
            running = false
        }
    }
    Surface(color = Color(240, 241, 243)) {
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
                            startTracking() // Push information up to main activity to start this tracking
                            TrackingData.isTracking = true
                            running = true
                            time = timeSinceStart()
                        }
                    }
                    WhiteButton("View Sprint History") {
                        stopTracking()
                        navController.navigate(Screen.HistoryScreen.route)
                    }

                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                elevation = 3.dp,
                shape = RectangleShape
            ) {
                Text(
                    text = if (hasRunToday)
                        "You've sprinted today, great job!"
                    else
                        "You haven't sprinted yet today!",
                    style = TextStyle(
                        color = Color(80, 80, 80),
                        fontSize = 20.sp,
                        fontFamily = roboto,
                        letterSpacing = 0.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}