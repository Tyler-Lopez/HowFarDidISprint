package com.company.howfardidisprint

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
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
import com.google.android.gms.nearby.messages.Distance
import kotlinx.coroutines.delay
import java.util.*


@ExperimentalPermissionsApi
@Composable
fun SprintScreen(
    startTracking: () -> Unit,
    stopTracking: () -> Unit,
) {
    // Update singleton with active run for purposes of tracking distance
    val context = LocalContext.current
    val mRunViewModel: RunViewModel = viewModel(
        factory = RunViewModelFactory(context.applicationContext as Application)
    )

    var distance by rememberSaveable { mutableStateOf(DistanceTracker.getTotalDistance()) }
    var time by rememberSaveable { mutableStateOf(-1) }
    var speed by rememberSaveable { mutableStateOf(DistanceTracker.getLocation()?.speed ?: 0F) }
    var running by rememberSaveable { mutableStateOf(DistanceTracker.getStartTime() != null) }
    // Marked as experimental https://www.youtube.com/watch?v=1UujnB__Lek 17:70
    val locationPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    // Screen orientation

    // WHEN TIME IS CHANGED, LAUNCH EFFECT
    // This coroutine listens for changes in a key, everytime time is changed invoke the co-routine again
    LaunchedEffect(key1 = time) {
        if (DistanceTracker.getStartTime() != null) {
            speed = DistanceTracker.getLocation()?.speed
                ?: 0F // Update speed variable with latest speed
            distance = DistanceTracker.getTotalDistance() // Update distance with latest speed
            time = DistanceTracker.timeSinceStart()
            delay(1000L) // Wait 1/10 second
            speed = DistanceTracker.getLocation()?.speed
                ?: 0F // Update speed variable with latest speed
            distance = DistanceTracker.getTotalDistance() // Update distance with latest speed
            time = DistanceTracker.timeSinceStart()
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
                SubHeader("RECORD A RUN")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
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
                        modifier = Modifier.weight(2f),
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
                // PLAY ICON
                // Shape of the icon is variable dependent on if...
                // 1. The app does not have GPS permission
                // 2. The app is not currently recording
                // 3. The app is currently recording
                CircleOrangeButton(
                    imageVector =
                    if (!running)
                        Icons.Rounded.PlayArrow
                    else Icons.Rounded.Pause,
                ) {
                    // Permission check
                    if (!locationPermissionState.hasPermission) {
                        locationPermissionState.launchPermissionRequest()
                    }
                    // If not yet tracking, begin tracking
                    else if (DistanceTracker.getStartTime() == null) {
                        DistanceTracker.setTime()
                        time = DistanceTracker.timeSinceStart()
                        running = true
                        startTracking() // Push information up to main activity to start this tracking
                    }
                    // Otherwise we are currently recording and should stop
                    else {
                        running = false
                       // if (distance > 100L) {
                            DistanceTracker.setEndTime()
                            mRunViewModel.insertRun(
                                Run(
                                    startTime = dateToTimestamp(Calendar.getInstance().time) ?: 0L,
                                    totalTime = time,
                                    distance = DistanceTracker.getTotalDistance()
                                )
                            )
                       // }
                        // RESET TIME TO -1
                        // This is done so when the button is pushed again and it is set to 0 that our co-routine starts tracking
                        time = -1
                        stopTracking()
                    }
                }
                // SUBTEXT
                // Provides context to the play icon button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = when {
                            !locationPermissionState.hasPermission -> "Requires GPS Permission"
                            running -> "Press to Save Run"
                            else -> "Press to Begin Recording"
                        },
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

