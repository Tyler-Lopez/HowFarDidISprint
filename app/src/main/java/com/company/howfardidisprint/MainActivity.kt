package com.company.howfardidisprint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.company.howfardidisprint.presentation.components.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.*
import java.util.*

class MainActivity : ComponentActivity() {


    @ExperimentalPermissionsApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the super class onCreate to complete the creation of activity
        super.onCreate(savedInstanceState)
        // Reset the DistanceTracker data only IF not changing configurations
        println("ON CREATE IS CALLED")
        if (!isChangingConfigurations) {
            println("ON CREATE IS CALLED WHEN NOT CHANGING CONFIGURATIONS")
            DistanceTracker.resetSingleton()
        } else {
            println("ON CREATE IS CALLED WHEN CHANGING CONFIGURATIONS")
        }

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


