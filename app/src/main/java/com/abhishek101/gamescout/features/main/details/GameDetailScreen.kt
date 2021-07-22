package com.abhishek101.gamescout.features.main.details

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.abhishek101.gamescout.theme.ScoutTheme

@Composable
fun GameDetailScreen(data: String) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { GameDetailTopBar() },
        content = {},
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun GameDetailTopBar() {
}
