package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.get

@Composable
fun GameDetailScreen(viewModel: GameDetailViewModel = get(), gameSlug: String?) {
    gameSlug?.let { viewModel.getGameDetails(it) }
    Text(text = "Game Detail Screen for $gameSlug", style = MaterialTheme.typography.h1)
}