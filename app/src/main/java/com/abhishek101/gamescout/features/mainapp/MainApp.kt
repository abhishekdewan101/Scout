package com.abhishek101.gamescout.features.mainapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import com.abhishek101.gamescout.components.bottomnavigation.BottomNavigationPager
import com.abhishek101.gamescout.components.bottomnavigation.BottomNavigationTabData
import com.abhishek101.gamescout.features.mainapp.home.HomeScreen
import com.abhishek101.gamescout.features.mainapp.lists.ListScreen
import com.abhishek101.gamescout.features.mainapp.search.SearchScreen

@Composable
fun MainApp() {
    val bottomTabs = listOf(
        BottomNavigationTabData(Icons.Outlined.Home, "Home", "Home"),
        BottomNavigationTabData(Icons.Outlined.Search, "Search", "Search"),
        BottomNavigationTabData(Icons.Outlined.List, "Lists", "Lists"),
    )
    BottomNavigationPager(
        bottomTabs = bottomTabs,
        pagerContent = { CreateMainPagerContent(index = it) }
    )
}

@Composable
fun CreateMainPagerContent(index: Int) {
    when (index) {
        0 -> HomeScreen()
        1 -> SearchScreen()
        2 -> ListScreen()
    }
}
