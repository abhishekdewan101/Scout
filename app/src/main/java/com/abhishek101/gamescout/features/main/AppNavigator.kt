@file:Suppress("MatchingDeclarationName")

package com.abhishek101.gamescout.features.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gamescout.features.main.AppScreens.DETAIL
import com.abhishek101.gamescout.features.main.AppScreens.HOME
import com.abhishek101.gamescout.features.main.AppScreens.VIEW_MORE
import com.abhishek101.gamescout.features.main.details.GameDetailScreen
import com.abhishek101.gamescout.features.main.home.HomeScreenScaffold
import com.abhishek101.gamescout.features.main.viewmore.ViewMoreScreen

enum class AppScreens {
    HOME,
    DETAIL,
    VIEW_MORE,
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME.name) {
        composable(route = HOME.name) {
            HomeScreenScaffold { screen, data ->
                navController.navigate("${screen.name}/$data")
            }
        }
        composable(
            route = "${DETAIL.name}/{slug}",
            arguments = listOf(
                navArgument(name = "slug") {
                    type = NavType.StringType
                }
            )
        ) {
            val slug = it.arguments?.getString("slug")
            require(slug != null) {
                "Cannot navigate to detail with a null slug value"
            }
            GameDetailScreen(data = slug)
        }

        composable(
            route = "${VIEW_MORE.name}/{listType}",
            arguments = listOf(
                navArgument(name = "listType") {
                    type = NavType.StringType
                }
            )
        ) {
            val listType = it.arguments?.getString("listType")
            require(listType != null) {
                "Cannot navigate to a view more screen with a null list type"
            }
            ViewMoreScreen(listType = listType)
        }
    }
}