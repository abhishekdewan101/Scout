package com.abhishek101.gamescout.components.bottomnavigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.abhishek101.gamescout.features.mainapp.MainAppBottomItems

@Composable
fun BottomNavigationPager(
    bottomTabs: Map<MainAppBottomItems, BottomNavigationTabData>,
    pagerContent: @Composable (NavHostController) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            pagerContent(navController)
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

            BottomNavigation(backgroundColor = Color(27, 127, 254)) {
                bottomTabs.forEach { (key, tab) ->
                    val color = if (currentRoute == key.name) {
                        MaterialTheme.colors.onBackground
                    } else {
                        MaterialTheme.colors.background
                    }
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                tab.icon,
                                tab.contentDescription,
                                tint = color
                            )
                        },
                        label = {
                            Text(tab.label, color = color)
                        },
                        selected = currentRoute == key.name,
                        onClick = {
                            navController.navigate(key.name) {
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    )
}
