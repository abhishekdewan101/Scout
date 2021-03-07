package com.abhishek101.gametracker.ui.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

@Composable
fun SplashScreen(viewModel: SplashViewModel) {

    val viewState = viewModel.isAuthenticationValid.collectAsState()

    viewModel.checkAuthentication()

    GameTrackerTheme {

        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "GameTracker",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.primary
                )
                if (!viewState.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 20.dp),
                        color = MaterialTheme.colors.primaryVariant
                    )
                }
            }
        }
    }
}