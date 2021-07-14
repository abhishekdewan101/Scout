package com.abhishek101.gamescout.features.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.authentication.AuthenticationViewModel
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.features.main.AppNavigator
import com.abhishek101.gamescout.features.preferenceselection.PreferenceSelectionNavigator
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthenticationScreen(viewModel: AuthenticationViewModel = get()) {
    val viewState by viewModel.viewState.collectAsState()

    SideEffect {
        viewModel.checkAuthentication()
    }

    when {
        !viewState.isAuthenticated && !viewState.isOnboardingCompleted -> SplashScreenView()
        !viewState.isOnboardingCompleted -> PreferenceSelectionNavigator()
        else -> AppNavigator()
    }
}

@Composable
private fun SplashScreenView() {
    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.primaryBackground,
        navigationBarColor = ScoutTheme.colors.primaryBackground,
        useDarkIcons = false
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(ScoutTheme.colors.primaryBackground)
        ) {
            Logo(modifier = Modifier.padding(bottom = 10.dp))
            CircularProgressIndicator(color = ScoutTheme.colors.progressIndicatorOnPrimaryBackground)
        }
    }
}

@Composable
private fun Logo(modifier: Modifier = Modifier) {
    val logo = painterResource(id = R.drawable.ic_splash_logo)
    Image(
        painter = logo,
        contentDescription = "Scout Logo",
        modifier = modifier.size(128.dp, 128.dp)
    )
}
