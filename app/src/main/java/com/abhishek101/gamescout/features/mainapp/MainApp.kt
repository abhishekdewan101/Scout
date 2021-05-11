package com.abhishek101.gamescout.features.mainapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhishek101.gamescout.R
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
fun MainApp() {
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
            ImageVector.Companion.vectorResource(id = R.drawable.library),
            "Library",
            "Library"
        ),
    )
    BottomNavigationPager(
        bottomTabs = bottomTabs
    ) {
        NavHost(navController = it, startDestination = MainAppBottomItems.HOME.name) {
            composable(MainAppBottomItems.HOME.name) { HomeScreen() }
            composable(MainAppBottomItems.LISTS.name) { LibraryScreen() }
            composable(MainAppBottomItems.SEARCH.name) { SearchScreen() }
        }
    }
}

@Composable
fun CreateMainPagerContent(index: Int) {
    when (index) {
        0 -> HomeScreen()
        1 -> SearchScreen()
        2 -> LibraryScreen()
    }
}
