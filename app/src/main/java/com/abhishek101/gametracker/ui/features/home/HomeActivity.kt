package com.abhishek101.gametracker.ui.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.abhishek101.gametracker.data.remote.AuthenticationApi
import com.abhishek101.gametracker.ui.components.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var authenticationApi: AuthenticationApi

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            Timber.d("Authentication Found - %s", authenticationApi.authenticateUser().toString())
        }
        setContent {
            HomeScreen()
        }
    }
}
