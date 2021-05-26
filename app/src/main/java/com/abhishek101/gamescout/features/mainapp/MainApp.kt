package com.abhishek101.gamescout.features.mainapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhishek101.gamescout.components.bottomnavigation.BottomNavigationPager
import com.abhishek101.gamescout.components.bottomnavigation.BottomNavigationTabData
import com.abhishek101.gamescout.features.mainapp.home.HomeScreen
import com.abhishek101.gamescout.features.mainapp.library.LibraryScreen
import com.abhishek101.gamescout.features.mainapp.search.SearchScreen

enum class MainAppBottomItems {
    HOME,
    SEARCH,
    LISTS
}

@Composable
fun MainApp(navigate: (String) -> Unit) {
    val bottomTabs = mapOf(
        MainAppBottomItems.HOME to BottomNavigationTabData(
            Icons.Outlined.Home,
            "Home",
            "Home"
        ),
        MainAppBottomItems.SEARCH to BottomNavigationTabData(
            Icons.Outlined.Search,
            "Search",
            "Search"
        ),
        MainAppBottomItems.LISTS to BottomNavigationTabData(
            Icons.Outlined.SportsEsports,
            "Library",
            "Library"
        ),
    )
    BottomNavigationPager(
        bottomTabs = bottomTabs
    ) {
        NavHost(navController = it, startDestination = MainAppBottomItems.HOME.name) {
            composable(MainAppBottomItems.HOME.name) { HomeScreen(navigate = navigate) }
            composable(MainAppBottomItems.LISTS.name) { LibraryScreen() }
            composable(MainAppBottomItems.SEARCH.name) { SearchScreen() }
        }
    }
}

@Composable
fun CreateMainPagerContent(index: Int) {
    when (index) {
        0 -> HomeScreen { _ -> }
        1 -> SearchScreen()
        2 -> LibraryScreen()
    }
}
