package com.abhishek101.gametracker.ui.features.home

import android.content.res.Configuration
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Text("Home Screen", style = MaterialTheme.typography.h6, color = Color.White)
        },
        bottomBar = {
            BottomAppBar {
                IconButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Games, "Game Lists")
                }
                IconButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Search, "Search")
                }
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL, device = Devices.PIXEL_4_XL)
@Composable
fun HomeScreenContentPreview() {
    GameTrackerTheme {
        HomeScreenContent()
    }
}