package com.abhishek101.gamescout.features.mainapp.navigator

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.abhishek101.core.repositories.ListType
import com.abhishek101.gamescout.features.mainapp.MainApp
import com.abhishek101.gamescout.features.mainapp.details.GameDetailScreen
import com.abhishek101.gamescout.features.mainapp.search.SearchScreen
import com.abhishek101.gamescout.features.mainapp.viewmore.ViewMoreScreen

enum class MainAppDestinations {
    ViewMore,
    GameDetail,
    MainApp,
    Search,
}

val LocalMainNavigator = compositionLocalOf<NavController> {
    error("No nav controller provided")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigator(setStatusBarColor: (Color, Boolean) -> Unit) {
    val mainNavController = rememberNavController()
    val backgroundColor = MaterialTheme.colors.background
    val useDarkIcon = MaterialTheme.colors.isLight
    SideEffect {
        setStatusBarColor(backgroundColor, useDarkIcon)
    }
    NavHost(
        navController = mainNavController,
        startDestination = MainAppDestinations.MainApp.name
    ) {
        composable(MainAppDestinations.MainApp.name) {
            CompositionLocalProvider(LocalMainNavigator provides mainNavController) {
                MainApp {
                    mainNavController.navigate(it)
                }
            }
        }

        composable(
            "${MainAppDestinations.ViewMore.name}/{listType}",
            arguments = listOf(navArgument("listType") { type = NavType.StringType })
        ) { backStackEntry ->
            val listType = backStackEntry.arguments?.getString("listType")
            ViewMoreScreen(listType = listType?.let { ListType.valueOf(it) }) {
                if (it.isEmpty()) {
                    mainNavController.popBackStack()
                } else {
                    mainNavController.navigate(it)
                }
            }
        }

        composable(
            "${MainAppDestinations.Search.name}/{searchTerm}",
            arguments = listOf(navArgument("searchTerm") { type = NavType.StringType })
        ) { backStackEntry ->
            val searchTerm = backStackEntry.arguments?.getString("searchTerm")
            if (searchTerm != null) {
                SearchScreen(searchTerm = searchTerm) {
                    if (it.isEmpty()) {
                        mainNavController.popBackStack()
                    } else {
                        mainNavController.navigate(it) 
                    }
                }
            } else {
                throw AssertionError("Search Cannot be Reached Without Search Term")
            }
        }


        composable(
            "${MainAppDestinations.GameDetail.name}/{gameSlug}",
            arguments = listOf(
                navArgument("gameSlug") { type = NavType.StringType }
            )
        ) {
            val gameSlug = it.arguments?.getString("gameSlug")
            if (gameSlug != null) {
                GameDetailScreen(gameSlug = gameSlug) { destination ->
                    if (destination.isEmpty()) {
                        mainNavController.popBackStack()
                    } else {
                        mainNavController.navigate(destination)
                    }
                }
            } else {
                Toast.makeText(
                    LocalContext.current,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
