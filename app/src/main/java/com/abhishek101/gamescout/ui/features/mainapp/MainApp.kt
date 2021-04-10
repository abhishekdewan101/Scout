package com.abhishek101.gamescout.ui.features.mainapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import com.abhishek101.gamescout.ui.components.bottomnavigation.BottomNavigationPager
import com.abhishek101.gamescout.ui.components.bottomnavigation.BottomNavigationTabData
import com.abhishek101.gamescout.ui.features.mainapp.home.HomeScreen
import com.abhishek101.gamescout.ui.features.mainapp.profile.ProfileScreen
import com.abhishek101.gamescout.ui.features.mainapp.search.SearchScreen

@Composable
fun MainApp() {
    val bottomTabs = listOf(
        BottomNavigationTabData(Icons.Outlined.Home, "Home", "Home"),
        BottomNavigationTabData(Icons.Outlined.Search, "Search", "Search"),
        BottomNavigationTabData(Icons.Outlined.Person, "Profile", "Profile"),
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
        2 -> ProfileScreen()
    }
}
