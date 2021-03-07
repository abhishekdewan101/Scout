package com.abhishek101.gametracker.ui.features.splash

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen(splashViewModel)
        }
    }
}
