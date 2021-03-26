package com.abhishek101.gametracker.ui.components

import androidx.compose.runtime.Composable
import com.abhishek101.gametracker.ui.components.navigation.MainNavigator
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

@Composable
fun MainApp() {
    GameTrackerTheme {
        MainNavigator()
    }
}
