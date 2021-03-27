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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.abhishek101.gametracker.ui.components.navigation.LocalMainNavController
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations
import org.koin.androidx.compose.get

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = get()
) {

    val isAuthenticationValid = viewModel.isAuthenticationValid

    viewModel.checkAuthentication()

    LocalContext

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .semantics { testTag = "SplashScreen" },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isAuthenticationValid.value) {
                Text(
                    "GameTracker",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.primary
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .semantics {
                            testTag = "loadingBar"
                        },
                    color = MaterialTheme.colors.primaryVariant
                )
            } else {
                LocalMainNavController.current.popBackStack()
                LocalMainNavController.current.navigate(MainNavigatorDestinations.OnBoarding.rawValue)
            }
        }
    }
}
