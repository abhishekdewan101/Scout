package com.abhishek101.gamescout.features.main.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abhishek101.gamescout.theme.ScoutTheme

@Composable
fun CollectionTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScoutTheme.colors.secondaryBackground)
    ) {
        Text("Collection Tab", color = ScoutTheme.colors.textOnSecondaryBackground)
    }
}
