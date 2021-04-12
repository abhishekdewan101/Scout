package com.abhishek101.gamescout.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import com.abhishek101.gamescout.ui.components.navigation.MainNavigator
import com.abhishek101.gamescout.ui.theme.Black
import com.abhishek101.gamescout.ui.theme.GameTrackerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTrackerTheme {
                window.statusBarColor = Black.toArgb()
                window.navigationBarColor = Black.toArgb()
                MainNavigator()
            }
        }
    }
}
