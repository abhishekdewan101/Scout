package com.abhishek101.gamescout.features.mainapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhishek101.gamescout.components.bottomnavigation.BottomNavigationPager
import com.abhishek101.gamescout.components.bottomnavigation.BottomNavigationTabData
import com.abhishek101.gamescout.features.mainapp.home.HomeScreen
import com.abhishek101.gamescout.features.mainapp.lists.ListScreen
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
            Icons.Outlined.List,
            "Lists",
            "Lists"
        ),
    )
    BottomNavigationPager(
        bottomTabs = bottomTabs
    ) {
        NavHost(navController = it, startDestination = MainAppBottomItems.HOME.name) {
            composable(MainAppBottomItems.HOME.name) { HomeScreen() }
            composable(MainAppBottomItems.LISTS.name) { ListScreen() }
            composable(MainAppBottomItems.SEARCH.name) { SearchScreen() }
        }
    }
}

@Composable
fun CreateMainPagerContent(index: Int) {
    when (index) {
        0 -> HomeScreen()
        1 -> SearchScreen()
        2 -> ListScreen()
    }
}
