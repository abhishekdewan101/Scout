package com.abhishek101.gamescout.features.mainapp.search

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.SafeArea
import org.koin.androidx.compose.get

@Composable
fun SearchScreen(searchScreenViewModel: SearchScreenViewModel = get()) {

    searchScreenViewModel.searchForGames("Zelda")

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        Text(
            "Search",
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
