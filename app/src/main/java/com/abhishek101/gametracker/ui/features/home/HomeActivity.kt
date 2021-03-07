package com.abhishek101.gametracker.ui.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.abhishek101.gametracker.data.AuthenticationRepository
import com.abhishek101.gametracker.ui.components.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var authenticationRepo: AuthenticationRepository

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            authenticationRepo.getAuthenticationData()
                .onEach {
                    if (it != null) {
                        Timber.d("Authentication Data is $it")
                    } else {
                        Timber.d("Firing authentication api calls")
                        authenticationRepo.authenticateUser()
                    }
                }.launchIn(CoroutineScope(Dispatchers.IO))
        }

        setContent {
            HomeScreen()
        }
    }
}
