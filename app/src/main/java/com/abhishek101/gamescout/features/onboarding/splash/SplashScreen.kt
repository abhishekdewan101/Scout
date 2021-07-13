package com.abhishek101.gamescout.features.onboarding.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.authentication.AuthenticationViewModel
import com.abhishek101.gamescout.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

@Composable
fun SplashScreen(
    viewModel: AuthenticationViewModel = get(),
    setStatusBarColor: (Color, Boolean) -> Unit,
    onAuthenticationValidated: () -> Unit
) {
    val isAuthenticationValid = viewModel.viewState.collectAsState()
    val statusBarColor = MaterialTheme.colors.primary

    SideEffect {
        setStatusBarColor(statusBarColor, false)
    }

    SplashScreenContent()

    LaunchedEffect(isAuthenticationValid.value) {
        if (isAuthenticationValid.value.isAuthenticated) {
            delay(1000)
            onAuthenticationValidated()
        } else {
            viewModel.checkAuthentication()
        }
    }
}

@Composable
fun SplashScreenContent() {
    val logo = painterResource(id = R.drawable.ic_splash_logo)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Image(painter = logo, contentDescription = "", modifier = Modifier.size(128.dp, 128.dp))
        CircularProgressIndicator(color = Color.White)
    }
}
