package com.abhishek101.gametracker.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations.GenreSelectionScreen
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations.HomeScreen
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations.PlatformSelectionScreen
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations.SplashScreen
import com.abhishek101.gametracker.ui.components.splash.SplashScreen
import com.abhishek101.gametracker.ui.features.home.HomeScreen
import com.abhishek101.gametracker.ui.features.onboarding.genre.GenreSelection
import com.abhishek101.gametracker.ui.features.onboarding.platform.PlatformSelection
import org.koin.androidx.compose.get

enum class MainNavigatorDestinations {
    SplashScreen,
    PlatformSelectionScreen,
    GenreSelectionScreen,
    HomeScreen
}

typealias UpdateOnBoardingComplete = () -> Unit

val LocalMainNavController = compositionLocalOf<NavController> {
    error("No nav host controller provided")
}

val LocalUpdateOnBoardingCompleted = compositionLocalOf<UpdateOnBoardingComplete> {
    error("No shared preferences provided")
}

val LocalSplashScreenDestination = compositionLocalOf<String> {
    error("No splash screen destination provided")
}

@Composable
fun MainNavigator() {
    val mainNavController = rememberNavController()
    val viewModel: MainNavigatorViewModel = get()
    val splashScreenDestination = if (viewModel.isOnBoardingComplete()) {
        HomeScreen.name
    } else {
        PlatformSelectionScreen.name
    }

    NavHost(
        navController = mainNavController,
        startDestination = SplashScreen.name
    ) {

        composable(SplashScreen.name) {
            CompositionLocalProvider(
                LocalMainNavController provides mainNavController,
                LocalSplashScreenDestination provides splashScreenDestination
            ) {
                SplashScreen()
            }
        }

        composable(PlatformSelectionScreen.name) {
            CompositionLocalProvider(LocalMainNavController provides mainNavController) {
                PlatformSelection()
            }
        }

        composable(GenreSelectionScreen.name) {
            CompositionLocalProvider(
                LocalMainNavController provides mainNavController,
                LocalUpdateOnBoardingCompleted provides viewModel::updateOnBoardingComplete
            ) {
                GenreSelection()
            }
        }

        composable(HomeScreen.name) {
            CompositionLocalProvider(LocalMainNavController provides mainNavController) {
                HomeScreen()
            }
        }
    }
}