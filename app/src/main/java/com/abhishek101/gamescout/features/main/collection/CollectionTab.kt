package com.abhishek101.gamescout.features.main.collection

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.abhishek101.gamescout.theme.ScoutTheme

@Composable
fun CollectionTab() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CollectionTopBar() },
        content = {
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun CollectionTopBar() {
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            Text(
                text = "Collection",
                fontWeight = FontWeight.Bold,
                color = ScoutTheme.colors.topBarTextColor
            )
        }
    )
}
