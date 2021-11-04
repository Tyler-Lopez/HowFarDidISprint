package com.company.howfardidisprint

import android.provider.Settings
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RunCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.company.howfardidisprint.model.RunDistance
import com.company.howfardidisprint.presentation.components.BottomBar
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    startTracking: () -> Unit,
    stopTracking: () -> Unit
) {

    Scaffold(

        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            Surface(color = Color(240, 241, 243)) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                ) {
                    composable(
                        route = Screen.SprintScreen.route,
                        exitTransition = null,
                        popEnterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            )
                        },
                        enterTransition = null
                    ) {
                        SprintScreen(
                            navController = navController,
                            startTracking = {
                                startTracking()
                            }, stopTracking = {
                                stopTracking()
                            }, runDistance = DistanceTracker.getRunType()
                        )
                    }
                    composable(
                        route = Screen.HistoryScreen.route,
                        exitTransition = null,
                        popEnterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            )
                        },
                        enterTransition = null
                    ) {
                        HistoryScreen(
                            navController = navController,
                            runDistance = DistanceTracker.getRunType()
                        )
                    }
                    composable(
                        route = Screen.SettingsScreen.route,
                    ) {
                        SettingsScreen(
                            navController = navController,
                        )
                    }
                    composable(
                        route = Screen.HomeScreen.route,
                    ) {
                       HomeScreen(
                            navController = navController,
                        )
                    }
                }
            }
        })
}