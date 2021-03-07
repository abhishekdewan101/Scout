package com.abhishek101.gametracker.ui.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.abhishek101.gametracker.data.AuthenticationRepository
import com.abhishek101.gametracker.ui.components.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var authenticationRepo: AuthenticationRepository

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}
