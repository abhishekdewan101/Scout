package com.abhishek101.gametracker.ui.features.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gametracker.ui.features.home.HomeScreen
import com.abhishek101.gametracker.ui.features.onboarding.genre.GenreSelection
import com.abhishek101.gametracker.ui.features.onboarding.platform.PlatformSelection
import org.koin.androidx.compose.get
import org.koin.core.parameter.parametersOf

enum class OnBoardingDestinations {
    HOME,
    PLATFORM,
    GENRE
}

@Composable
fun OnBoarding() {
    val context = LocalContext.current
    val viewModel: OnBoardingViewModel = get(parameters = { parametersOf(context) })
    val navController = rememberNavController()
    Column(modifier = Modifier.semantics { testTag = "OnBoardingScreen" }) {
        NavHost(
            navController = navController,
            startDestination = if (viewModel.getOnBoardingComplete()) {
                OnBoardingDestinations.HOME.name
            } else {
                OnBoardingDestinations.PLATFORM.name
            }

        ) {
            composable(OnBoardingDestinations.HOME.name) {
                HomeScreen()
            }
            composable(OnBoardingDestinations.PLATFORM.name) {
                PlatformSelection() {
                    navController.popBackStack()
                    navController.navigate(OnBoardingDestinations.GENRE.name)
                }
            }
            composable(OnBoardingDestinations.GENRE.name) {
                GenreSelection() {
                    viewModel.updateOnBoardingCompleted()
                    navController.popBackStack()
                    navController.navigate(OnBoardingDestinations.HOME.name)
                }
            }
        }
    }
}