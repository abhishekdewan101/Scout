package com.abhishek101.gamescout.features.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gamescout.features.genreselection.GenreSelection
import com.abhishek101.gamescout.features.mainapp.navigator.MainNavigator
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.GenreSelectionScreen
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.MainAppScreen
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.PlatformSelectionScreen
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.SplashScreen
import com.abhishek101.gamescout.features.onboarding.splash.SplashScreen
import com.abhishek101.gamescout.features.platformselection.PlatformSelection
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

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

@Composable
fun OnBoardingNavigator(viewModel: OnBoardingNavigatorViewModel = getViewModel()) {
    val onBoardingNavigator = rememberNavController()
    val splashScreenDestination = if (viewModel.isOnBoardingComplete()) {
        MainAppScreen.name
    } else {
        PlatformSelectionScreen.name
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(color = Color.Black, darkIcons = false)
    }

    NavHost(navController = onBoardingNavigator, startDestination = SplashScreen.name) {
        composable(SplashScreen.name) {
            SplashScreen(
                setStatusBarColor = { color: Color, useDarkIcons: Boolean ->
                    systemUiController.setStatusBarColor(color, useDarkIcons)
                },
                onAuthenticationValidated = {
                    onBoardingNavigator.popBackStack()
                    onBoardingNavigator.navigate(splashScreenDestination.toString())
                }
            )
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
                MainNavigator { color: Color, useDarkIcons: Boolean ->
                    systemUiController.setStatusBarColor(color, useDarkIcons)
                }
            }
        }
    }
}
