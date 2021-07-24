package com.abhishek101.gamescout.features.main.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.theme.ScoutTheme
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun GameDetailViewContainer(data: String) {
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.topBarBackground,
        navigationBarColor = ScoutTheme.colors.topBarBackground,
        useDarkIcons = false
    ) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = { AddGameScreen() },
            scrimColor = ScoutTheme.colors.modalBottomSheetBackground,
            sheetShape = MaterialTheme.shapes.small
        ) {
            GameDetailScreen(data = data) { bottomSheetValue ->
                scope.launch {
                    modalBottomSheetState.animateTo(bottomSheetValue)
                }
            }
        }
    }
}
