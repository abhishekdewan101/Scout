package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun GameDetailScreen(gameSlug: String?) {
    Text(text = "Game Detail Screen for $gameSlug", style = MaterialTheme.typography.h1)
}