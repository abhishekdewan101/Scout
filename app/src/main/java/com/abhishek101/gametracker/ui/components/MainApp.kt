package com.abhishek101.gametracker.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.getBackStackEntry
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.features.home.HomeScreen
import com.abhishek101.gametracker.ui.features.splash.SplashScreen
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

enum class NavDestinations {
    SPLASH,
    HOME
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    GameTrackerTheme {
        NavHost(navController = navController, startDestination = NavDestinations.SPLASH.name) {
            composable(NavDestinations.SPLASH.name) {
                SplashScreen(navController.getBackStackEntry(NavDestinations.SPLASH.name)) {
                    navController.popBackStack()
                    navController.navigate(NavDestinations.HOME.name)
                }
            }
            composable(NavDestinations.HOME.name) {
                HomeScreen()
            }
        }
    }
}
