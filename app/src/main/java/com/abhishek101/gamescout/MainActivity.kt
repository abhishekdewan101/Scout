package com.abhishek101.gamescout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.abhishek101.gamescout.features.authentication.AuthenticationScreen
import com.abhishek101.gamescout.theme.ScoutTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ScoutTheme {
                ProvideWindowInsets {
                    Box(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .statusBarsPadding()
                    ) {
                        AuthenticationScreen()
                    }
                }
            }
        }
    }
}
