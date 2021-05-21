package com.abhishek101.gamescout.features.onboarding.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.features.onboarding.LocalOnBoardingNavigator
import com.abhishek101.gamescout.features.onboarding.LocalSplashScreenDestination
import com.abhishek101.gamescout.theme.GameTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun SplashScreen(viewModel: SplashScreenVM = get()) {

    val isAuthenticationValid = viewModel.isAuthenticationValid
    val navController = LocalOnBoardingNavigator.current
    val splashScreenDestination = LocalSplashScreenDestination.current

    viewModel.checkAuthentication()

    SplashScreenContent()
    if (isAuthenticationValid.value) {
        LaunchedEffect(splashScreenDestination) {
            delay(2000)
            navController.popBackStack()
            navController.navigate(splashScreenDestination)
        }
    }
}

@Composable
fun SplashScreenContent() {
    Box(modifier = Modifier.fillMaxWidth()) {
        val image: Painter = painterResource(id = R.drawable.background)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(
                        17,
                        27,
                        27
                    ).copy(alpha = 0.4f)
                ), // Fixme: Colors need to come from a theme. We need to create a material theme like thing.
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Scout",
                style = MaterialTheme.typography.h2.copy(
                    color = Color(27, 127, 254),
                    fontWeight = FontWeight.Bold
                )
            )
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 20.dp)
                    .semantics { testTag = "loadingBar" }
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun Splash_Screen_Invalid_Authentication_Preview() {
    GameTrackerTheme {
        SplashScreenContent()
    }
}
