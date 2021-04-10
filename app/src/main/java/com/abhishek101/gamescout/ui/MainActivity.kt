package com.abhishek101.gamescout.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.abhishek101.gamescout.ui.components.navigation.MainNavigator
import com.abhishek101.gamescout.ui.theme.GameTrackerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTrackerTheme {
                window.statusBarColor = Color(0xFF0e0d0d).toArgb()
                window.navigationBarColor = Color(0xFF0e0d0d).toArgb()
                MainNavigator()
            }
        }
    }
}
