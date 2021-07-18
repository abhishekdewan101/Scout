package com.abhishek101.gamescout.features.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abhishek101.gamescout.theme.ScoutTheme

@Composable
fun SearchTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScoutTheme.colors.secondaryBackground)
    ) {
        Text("Search Tab", color = ScoutTheme.colors.textOnSecondaryBackground)
    }
}
