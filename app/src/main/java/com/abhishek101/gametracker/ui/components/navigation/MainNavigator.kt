package com.abhishek101.gametracker.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.features.onboarding.OnBoarding
import com.abhishek101.gametracker.ui.features.splash.SplashScreen

enum class MainNavigatorDestinations(val rawValue: String) {
    Splash("Splash"),
    OnBoarding("Onbarding")
}

val LocalMainNavController = compositionLocalOf<NavController> {
    error("No nav host controller provided")
}

@Composable
fun MainNavigator() {
    val mainNavController = rememberNavController()
    NavHost(
        navController = mainNavController,
        startDestination = MainNavigatorDestinations.Splash.rawValue
    ) {

        composable(MainNavigatorDestinations.Splash.rawValue) {
            CompositionLocalProvider(LocalMainNavController provides mainNavController) {
                SplashScreen()
            }
        }

        composable(MainNavigatorDestinations.OnBoarding.rawValue) {
            OnBoarding()
        }
    }
}