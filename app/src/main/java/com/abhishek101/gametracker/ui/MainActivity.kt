package com.abhishek101.gametracker.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.abhishek101.gametracker.ui.components.navigation.MainNavigator
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTrackerTheme {
                MainNavigator()
            }
        }
    }
}
