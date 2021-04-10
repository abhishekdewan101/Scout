package com.abhishek101.gamescout.ui.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.navigate
import com.abhishek101.gamescout.ui.components.navigation.LocalMainNavController
import com.abhishek101.gamescout.ui.components.navigation.LocalSplashScreenDestination
import com.abhishek101.gamescout.ui.theme.GameTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun SplashScreen(viewModel: SplashViewModel = get()) {

    val isAuthenticationValid = viewModel.isAuthenticationValid
    val navController = LocalMainNavController.current
    val splashScreenDestination = LocalSplashScreenDestination.current
    val coroutineScope = rememberCoroutineScope()

    viewModel.checkAuthentication()

    SplashScreenContent(isAuthenticationValid = isAuthenticationValid.value) {
        coroutineScope.launch {
            delay(1500)
            navController.popBackStack()
            navController.navigate(splashScreenDestination)
        }
    }
}

@Composable
fun SplashScreenContent(isAuthenticationValid: Boolean, navigateForward: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .semantics { testTag = "SplashScreen" }
    ) {
        Text(
            "Scout",
            style = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 48.sp)
        )
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(40.dp)
                .padding(top = 20.dp)
                .semantics { testTag = "loadingBar" }
        )
        if (isAuthenticationValid) {
            navigateForward()
        }
    }
}

@Preview(showBackground = false)
@Composable
fun Splash_Screen_Invalid_Authentication_Preview() {
    GameTrackerTheme {
        SplashScreenContent(isAuthenticationValid = false)
    }
}
