package com.abhishek101.gamescout.features.main.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.features.main.collection.CollectionTab
import com.abhishek101.gamescout.features.main.discover.DiscoverTab
import com.abhishek101.gamescout.features.main.home.HomeScreenRoutes.Collection
import com.abhishek101.gamescout.features.main.home.HomeScreenRoutes.Discover
import com.abhishek101.gamescout.features.main.home.HomeScreenRoutes.Search
import com.abhishek101.gamescout.features.main.search.SearchTab
import com.abhishek101.gamescout.theme.ScoutTheme

@Composable
fun HomeScreenScaffold(navigateToScreen: (AppScreens, String) -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.bottomNavBarBackground,
        navigationBarColor = ScoutTheme.colors.bottomNavBarBackground
    ) {
        Scaffold(
            backgroundColor = ScoutTheme.colors.secondaryBackground,
            scaffoldState = scaffoldState,
            bottomBar = {
                HomeBottomNavBar(selectedRoute = extractSelectedRoute(entry = currentBackStackEntry)) {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            content = { HomePagerContent(navController = navController, navigateToScreen = navigateToScreen) }
        )
    }
}

@Composable
private fun HomePagerContent(navController: NavHostController, navigateToScreen: (AppScreens, String) -> Unit) {
    NavHost(navController = navController, startDestination = Discover.route) {
        composable(route = Discover.route) {
            DiscoverTab(navigateToScreen = navigateToScreen)
        }
        composable(route = Search.route) {
            SearchTab()
        }
        composable(route = Collection.route) {
            CollectionTab()
        }
    }
}

@Composable
private fun HomeBottomNavBar(selectedRoute: String?, onTabSelected: (String) -> Unit) {
    val screens = listOf(Discover, Search, Collection)
    BottomNavigation(backgroundColor = ScoutTheme.colors.bottomNavBarBackground) {
        screens.forEach {
            val selected = it.route == selectedRoute
            val color = if (selected) ScoutTheme.colors.bottomItemSelected else ScoutTheme.colors.bottomItemUnselected
            BottomNavigationItem(
                selected = selected,
                onClick = { onTabSelected(it.route) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title,
                        tint = color
                    )
                },
                label = {
                    Text(it.title, color = color)
                }
            )
        }
    }
}

fun extractSelectedRoute(entry: NavBackStackEntry?): String? {
    return entry?.destination?.route
}
