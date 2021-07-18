package com.abhishek101.gamescout.features.main.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Podcasts
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideogameAsset
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeScreenRoutes(val route: String, val title: String, val icon: ImageVector) {
    object Discover : HomeScreenRoutes(route = "discover", title = "Discover", icon = Icons.Outlined.Podcasts)
    object Search : HomeScreenRoutes(route = "search", title = "Search", icon = Icons.Outlined.Search)
    object Collection : HomeScreenRoutes(route = "collection", title = "Collection", icon = Icons.Outlined.VideogameAsset)
}
