package com.abhishek101.gamescout.components.bottomnavigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
            val currentBackStack = navController.currentBackStackEntryAsState()

            val currentRoute = currentBackStack.value?.destination?.route

            BottomNavigation(backgroundColor = MaterialTheme.colors.primaryVariant) {
                bottomTabs.forEach { (key, tab) ->
                    val color = if (currentRoute == key.name) {
                        Color.White
                    } else {
                        Color.White.copy(alpha = 0.5f)
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
                                navController.graph.startDestinationRoute?.let { popUpTo(it) }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    )
}
