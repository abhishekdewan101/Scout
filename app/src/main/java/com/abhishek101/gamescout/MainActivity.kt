package com.abhishek101.gamescout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import com.abhishek101.gamescout.features.onboarding.OnBoardingNavigator
import com.abhishek101.gamescout.theme.GameTrackerTheme

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTrackerTheme {
                OnBoardingNavigator()
            }
        }
    }
}
