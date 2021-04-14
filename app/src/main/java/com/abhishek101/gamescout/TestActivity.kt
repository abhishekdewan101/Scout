package com.abhishek101.gamescout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.TouchableImage
import com.abhishek101.gamescout.theme.GameTrackerTheme
import timber.log.Timber

class TestActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTrackerTheme {
                Column {
                    TouchableImage(
                        url = "https://img.icons8.com/metro/452/batman-new.png",
                        100.dp,
                        100.dp,
                        10.dp,
                        MaterialTheme.colors.secondary,
                        Color.Red,
                    ) {
                        Timber.d("Something was touched")
                    }

                    TouchableImage(
                        url = "https://img.icons8.com/metro/452/batman-new.png",
                        150.dp,
                        200.dp,
                        10.dp,
                        MaterialTheme.colors.secondary,
                        Color.Green,
                    ) {
                        Timber.d("Something was touched")
                    }

                    TouchableImage(
                        url = "https://img.icons8.com/metro/452/batman-new.png",
                        200.dp,
                        100.dp,
                        10.dp, null, null) {
                        Timber.d("Something was touched")
                    }
                }

            }
        }
    }
}