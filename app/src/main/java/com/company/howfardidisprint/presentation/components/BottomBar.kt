package com.company.howfardidisprint.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

val BottomNavItems = listOf(
    BottomNavItem(
        label = "Home",
        icon = Icons.Filled.Home,
        route = "home_screen"
    ),
    BottomNavItem(
        label = "Run",
        icon = Icons.Filled.RunCircle,
        route = "sprint_screen"
    ),
    BottomNavItem(
        label = "Settings",
        icon = Icons.Rounded.Settings,
        route = "settings_screen"
    )
)

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier.height(75.dp)
    ) {
        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route


        BottomNavItems.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route) },
                selectedContentColor = Color(250, 82, 7),
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.label,
                            modifier = Modifier
                                .weight(2.0f)
                                .size(50.dp)
                        )
                        Text(
                            text = it.label,
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
            )

        }
    }
}


data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

