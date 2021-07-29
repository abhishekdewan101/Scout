package com.abhishek101.gamescout.features.main.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@ExperimentalMaterialApi
@Composable
fun GameDetailViewContainer(
    viewModel: GameDetailViewModel = get(),
    data: String,
    navigateBack: () -> Unit,
    navigateToScreen: (AppScreens, Any) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.topBarBackground,
        navigationBarColor = ScoutTheme.colors.topBarBackground,
        useDarkIcons = false
    ) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = {
                AddGameScreen(viewModel = viewModel) {
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            },
            scrimColor = ScoutTheme.colors.modalBottomSheetBackground,
            sheetBackgroundColor = ScoutTheme.colors.modalBottomSheetBackground,
            sheetShape = MaterialTheme.shapes.small
        ) {
            GameDetailScreen(
                viewModel = viewModel,
                data = data,
                navigateBack = navigateBack,
                navigateToScreen = navigateToScreen
            ) { bottomSheetValue ->
                scope.launch {
                    modalBottomSheetState.animateTo(bottomSheetValue)
                }
            }
        }
    }
}
