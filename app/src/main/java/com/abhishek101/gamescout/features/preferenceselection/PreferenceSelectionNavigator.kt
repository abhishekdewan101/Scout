@file:Suppress("MatchingDeclarationName")

package com.abhishek101.gamescout.features.preferenceselection

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gamescout.features.mainapp.navigator.MainNavigator
import com.abhishek101.gamescout.features.preferenceselection.PreferenceSelectionStage.GENRE
import com.abhishek101.gamescout.features.preferenceselection.PreferenceSelectionStage.HOME
import com.abhishek101.gamescout.features.preferenceselection.PreferenceSelectionStage.PLATFORM

enum class PreferenceSelectionStage {
    PLATFORM,
    GENRE,
    HOME,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PreferenceSelectionNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PLATFORM.name) {
        composable(PLATFORM.name) {
            PlatformSelectionScreen {
                navController.navigate(GENRE.name)
            }
        }

        composable(GENRE.name) {
            GenreSelectionScreen {
                navController.navigate(HOME.name)
            }
        }

        composable(HOME.name) {
            MainNavigator()
        }
    }
}
