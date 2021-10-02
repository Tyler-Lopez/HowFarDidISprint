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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.HowFarDidISprintTheme
import com.company.howfardidisprint.ui.theme.roboto
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.location.*
import kotlinx.coroutines.delay
import java.util.*

class MainActivity : ComponentActivity() {


    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberAnimatedNavController()

            HowFarDidISprintTheme {

                // A surface container using the 'background' color from the theme
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
                        Navigation(
                            navController = navController,
                            startTracking = {
                                startTracking()
                            },
                            stopTracking = {
                                stopTracking()
                            })
                    }
                )
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
        refreshSingleton()
    }
}



