package com.company.howfardidisprint

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.company.howfardidisprint.model.RunDistance
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
    var runDistance = RunDistance.MILE
    Scaffold(

        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Run a Day",
                    fontWeight = FontWeight.Bold
                )
            }, backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.RunSelection.route)
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
            Surface(color = Color(240, 241, 243)) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screen.SprintScreen.route,
                ) {
                    composable(
                        route = Screen.SprintScreen.route,
                        exitTransition = null,
                        popEnterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            ) + fadeIn(initialAlpha = 0.0f, animationSpec = tween(400))
                        },
                        enterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            ) + fadeIn(initialAlpha = 0.0f, animationSpec = tween(400))
                        }
                    ) {
                        SprintScreen(
                            navController = navController,
                            startTracking = {
                                startTracking()
                            }, stopTracking = {
                                stopTracking()
                            }, runDistance = runDistance
                        )
                    }
                    composable(
                        route = Screen.HistoryScreen.route,
                        exitTransition = null,
                        popEnterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            ) + fadeIn(initialAlpha = 0.0f, animationSpec = tween(400))
                        },
                        enterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            ) + fadeIn(initialAlpha = 0.0f, animationSpec = tween(400))
                        }
                    ) {
                        HistoryScreen(
                            navController = navController,
                            runDistance = runDistance
                        )
                    }
                    composable(
                        route = Screen.RunSelection.route,
                        exitTransition = null,
                        popEnterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            ) + fadeIn(initialAlpha = 0.0f, animationSpec = tween(400))
                        },
                        enterTransition = { _, _ ->
                            slideInHorizontally(
                                initialOffsetX = { -1200 },
                                animationSpec = tween(400)
                            ) + fadeIn(initialAlpha = 0.0f, animationSpec = tween(400))
                        }
                    ) {
                        RunSelectionScreen(
                            navController = navController,
                            onUpdateDistance = {
                                runDistance = it
                            }
                        )
                    }
                }
            }
        })
}