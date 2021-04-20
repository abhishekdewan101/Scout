package com.abhishek101.gamescout.features.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gamescout.features.genreselection.GenreSelection
import com.abhishek101.gamescout.features.mainapp.MainApp
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.GenreSelectionScreen
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.MainAppScreen
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.PlatformSelectionScreen
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.SplashScreen
import com.abhishek101.gamescout.features.onboarding.splash.SplashScreen
import com.abhishek101.gamescout.features.platformselection.PlatformSelection
import org.koin.androidx.compose.get

enum class OnBoardingDestinations {
    SplashScreen,
    PlatformSelectionScreen,
    GenreSelectionScreen,
    MainAppScreen
}

typealias UpdateOnBoardingComplete = () -> Unit

val LocalOnBoardingNavigator = compositionLocalOf<NavController> {
    error("No nav host controller provided")
}

val LocalUpdateOnBoardingCompleted = compositionLocalOf<UpdateOnBoardingComplete> {
    error("No shared preferences provided")
}

val LocalSplashScreenDestination = compositionLocalOf<String> {
    error("No splash screen destination provided")
}

@Composable
fun OnBoardingNavigator() {
    val onBoardingNavigator = rememberNavController()
    val viewModel: OnBoardingNavigatorViewModel = get()
    val splashScreenDestination = if (viewModel.isOnBoardingComplete()) {
        MainAppScreen.name
    } else {
        PlatformSelectionScreen.name
    }

    NavHost(
        navController = onBoardingNavigator,
        startDestination = SplashScreen.name
    ) {

        composable(SplashScreen.name) {
            CompositionLocalProvider(
                LocalOnBoardingNavigator provides onBoardingNavigator,
                LocalSplashScreenDestination provides splashScreenDestination
            ) {
                SplashScreen()
            }
        }

        composable(PlatformSelectionScreen.name) {
            CompositionLocalProvider(LocalOnBoardingNavigator provides onBoardingNavigator) {
                PlatformSelection()
            }
        }

        composable(GenreSelectionScreen.name) {
            CompositionLocalProvider(
                LocalOnBoardingNavigator provides onBoardingNavigator,
                LocalUpdateOnBoardingCompleted provides viewModel::updateOnBoardingComplete
            ) {
                GenreSelection()
            }
        }

        composable(MainAppScreen.name) {
            CompositionLocalProvider(LocalOnBoardingNavigator provides onBoardingNavigator) {
                MainApp()
            }
        }
    }
}
