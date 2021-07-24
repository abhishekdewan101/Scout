package com.abhishek101.gamescout.features.main.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.theme.ScoutTheme

@Composable
fun AddGameScreen() {
    BoxWithConstraints {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight - 20.dp)
                .background(ScoutTheme.colors.secondaryBackground)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Add Game", color = ScoutTheme.colors.textOnSecondaryBackground)
        }
    }
}
