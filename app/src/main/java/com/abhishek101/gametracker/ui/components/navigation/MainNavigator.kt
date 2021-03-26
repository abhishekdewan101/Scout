package com.abhishek101.gametracker.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.features.onboarding.OnBoarding
import com.abhishek101.gametracker.ui.features.splash.SplashScreen

enum class MainNavigatorDestinations(val rawValue: String) {
    Splash("Splash"),
    OnBoarding("Onbarding")
}

@Composable
fun MainNavigator() {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = MainNavigatorDestinations.Splash.rawValue) {

        composable(MainNavigatorDestinations.Splash.rawValue) {
            SplashScreen {
                mainNavController.popBackStack()
                mainNavController.navigate(MainNavigatorDestinations.OnBoarding.rawValue)
            }
        }

        composable(MainNavigatorDestinations.OnBoarding.rawValue) {
            OnBoarding()
        }
    }
}