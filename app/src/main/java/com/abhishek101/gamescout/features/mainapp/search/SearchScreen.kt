package com.abhishek101.gamescout.features.mainapp.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.SafeArea

@Composable
fun SearchScreen() {
    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Text(
                    "Search",
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
