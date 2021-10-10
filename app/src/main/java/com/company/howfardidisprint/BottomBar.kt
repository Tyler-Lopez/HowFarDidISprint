package com.company.howfardidisprint


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        route = "runselection_screen"
    ),
    BottomNavItem(
        label = "Settings",
        icon = Icons.Rounded.Settings,
        route = "settings_screen"
    )
)

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(

        // set background color
        backgroundColor = Color.White, modifier = Modifier.shadow(10.dp)
    ) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared

        BottomNavItems.forEach() { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                        modifier = Modifier.size(30.dp),
                        tint = Color(
                            250, 82, 7
                        )
                    )
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

