package com.company.howfardidisprint

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.company.howfardidisprint.model.*
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.roboto
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import java.util.*


@ExperimentalPermissionsApi
@Composable
fun SprintScreen(
    navController: NavController,
    startTracking: () -> Unit,
    stopTracking: () -> Unit,
    runDistance: RunDistance = RunDistance.QUARTERMILE,
) {
    // Update singleton with active run for purposes of tracking distance
    DistanceTracker.runDistance = runDistance

    val context = LocalContext.current
    val mRunViewModel: RunViewModel = viewModel(
        factory = RunViewModelFactory(context.applicationContext as Application)
    )
    mRunViewModel.filterDistance(runDistance, SortType.BY_DATE)
    var leaderBoards = mRunViewModel.data.observeAsState(listOf()).value


    // This is kind of a hacky, bad solution to ensuring this is updated after the user runs
    var hasRunToday by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = leaderBoards.size) {
        hasRunToday = hasRunToday(leaderBoards)
    }

    var distance by rememberSaveable { mutableStateOf(DistanceTracker.totalDistance) }
    var time by rememberSaveable { mutableStateOf(-1) }
    var speed by rememberSaveable { mutableStateOf(DistanceTracker.latestSpeed) }
    var running by rememberSaveable { mutableStateOf(TrackingData.isTracking) }
    // Marked as experimental https://www.youtube.com/watch?v=1UujnB__Lek 17:70
    val locationPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    // Screen orientation
    val configuration = LocalConfiguration.current
    var isLandscape by remember {
        mutableStateOf(false)
    }
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> isLandscape = true
        else -> false
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
        } else { // This would occur if we have reached over > 400 meters
            distance = DistanceTracker.totalDistance
            if (distance >= DistanceTracker.runDistance.distance) {
                mRunViewModel.insertRun(
                    Run(
                        startTime = dateToTimestamp(Calendar.getInstance().time) ?: 0L,
                        totalTime = time,
                        distance = runDistance
                    )
                )
            }
            stopTracking() // Push information up to main activity to close this tracking
            running = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 3.dp,
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                SubHeader("$runDistance")
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
                    if (isLandscape) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            OrangeButton(
                                value =
                                if (!locationPermissionState.hasPermission)
                                    "Allow GPS Permission"
                                else if (!running)
                                    "Start"
                                else "Stop"
                            ) {
                                // Permission check
                                if (!locationPermissionState.hasPermission) {
                                    locationPermissionState.launchPermissionRequest()
                                }
                                // If not yet tracking, begin tracking
                                else if (!TrackingData.isTracking) {
                                    TrackingData.trackingStartedOn =
                                        System.currentTimeMillis()
                                    startTracking() // Push information up to main activity to start this tracking
                                    TrackingData.isTracking = true
                                    running = true
                                    time = timeSinceStart()
                                }
                            }
                            WhiteButton("View $runDistance History", {
                                stopTracking()
                                navController.navigate(Screen.HistoryScreen.route)
                            }, PaddingValues(10.dp))
                        }
                    }
                }

                if (!isLandscape) {
                    OrangeButton(
                        value =
                        if (!locationPermissionState.hasPermission)
                            "Allow GPS Permission"
                        else if (!running)
                            "Start"
                        else "Stop"
                    ) {
                        // Permission check
                        if (!locationPermissionState.hasPermission) {
                            locationPermissionState.launchPermissionRequest()
                        }
                        // If not yet tracking, begin tracking
                        else if (!TrackingData.isTracking) {
                            TrackingData.trackingStartedOn =
                                System.currentTimeMillis()
                            startTracking() // Push information up to main activity to start this tracking
                            TrackingData.isTracking = true
                            running = true
                            time = timeSinceStart()
                        }
                    }
                    WhiteButton("View $runDistance History",
                        onClick = {
                            stopTracking()
                            navController.navigate(Screen.HistoryScreen.route)
                        }, PaddingValues(10.dp)
                    )
                    FullWidthCard(
                        string = if (hasRunToday)
                            "You've run today, great job!"
                        else
                            "You haven't run yet today!"
                    )
                }

            }
        }
    }
}

