package com.abhishek101.gamescout.features.authentication

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.abhishek101.core.viewmodels.authentication.AuthenticationViewModel
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun AuthenticationScreen(viewModel: AuthenticationViewModel = get()) {
    val viewState = viewModel.viewState.collectAsState()
    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.brandPrimary,
        navigationBarColor = ScoutTheme.colors.brandPrimary,
        useDarkIcons = false
    ) {
        Text("Testing ")
    }
}
