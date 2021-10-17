package com.company.howfardidisprint

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RunCircle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.HowFarDidISprintTheme
import com.company.howfardidisprint.ui.theme.roboto
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.*
import kotlinx.coroutines.delay
import java.util.*

class MainActivity : ComponentActivity() {


    @ExperimentalPermissionsApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the super class onCreate to complete the creation of activity
        super.onCreate(savedInstanceState)
        // Reset the DistanceTracker data
        DistanceTracker.resetSingleton()
        setContent {
            Navigation(
                navController = rememberAnimatedNavController(),
                startTracking = {
                    startTracking()
                },
                stopTracking = {
                    stopTracking()
                })
        }
    }

    override fun onPause() {
        super.onPause()
        println("ON PAUSE IS CALLED")
        // https://stackoverflow.com/questions/10971284/detect-activity-being-paused-due-to-configuration-change
    }

    override fun onStop() {
        super.onStop()
        println("ON STOP IS CALLED")
       // if (!isChangingConfigurations)
        //    stopTracking()
    }

    override fun onResume() {
        super.onResume()
        println("ON RESUME IS CALLED")
        //if (DistanceTracker.endTime != null) {
        //    stopTracking()
       // }
    }

    private fun startTracking() {
        startService(
            LocationTrackingService.getIntent(
                this@MainActivity
            )
        )
    }

    private fun stopTracking() {
        LocationTrackingService.stopTracking(this@MainActivity) // Stop tracking the location!
        stopService(LocationTrackingService.getIntent(this@MainActivity))
        DistanceTracker.resetSingleton()
    }
}


