package com.abhishek101.gametracker.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.features.onboarding.OnBoarding
import com.abhishek101.gametracker.ui.features.splash.SplashScreen
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

enum class NavDestinations {
    SPLASH,
    ONBOARDING,
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    GameTrackerTheme {
        NavHost(navController = navController, startDestination = NavDestinations.SPLASH.name) {
            composable(NavDestinations.SPLASH.name) {
                SplashScreen {
                    navController.popBackStack()
                    navController.navigate(NavDestinations.ONBOARDING.name)
                }
            }
            composable(NavDestinations.ONBOARDING.name) {
                OnBoarding()
            }
        }
    }
}
