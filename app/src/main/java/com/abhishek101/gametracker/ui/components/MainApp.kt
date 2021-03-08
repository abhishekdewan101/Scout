package com.abhishek101.gametracker.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.features.home.HomeScreen
import com.abhishek101.gametracker.ui.features.splash.SplashScreen
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

@Composable
fun MainApp() {
    val navController = rememberNavController()
    GameTrackerTheme {
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen(navController)
            }
            composable("home") {
                HomeScreen()
            }
        }
    }
}
