package com.abhishek101.gamescout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.abhishek101.gamescout.theme.GameTrackerTheme

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTrackerTheme {
            }
        }
    }
}
