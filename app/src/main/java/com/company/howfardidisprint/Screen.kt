package com.company.howfardidisprint

sealed class Screen(val route: String) {
    object SprintScreen : Screen("sprint_screen")
    object HistoryScreen : Screen("history_screen")
    object RunSelection : Screen("runselection_screen")
    object SettingsScreen : Screen("settings_screen")
    object HomeScreen : Screen("home_screen")


    // Invoked to pass string arguments(section key) to screens
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg")}
        }
    }
}
