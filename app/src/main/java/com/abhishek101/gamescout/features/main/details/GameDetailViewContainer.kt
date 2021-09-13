package com.abhishek101.gamescout.features.main.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.gamescout.design.system.SystemUiControlView
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
    val viewState by viewModel.viewState.collectAsState()
    val libraryState by viewModel.libraryState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.constructGameDetails(slug = data)
    }

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.topBarBackground,
        navigationBarColor = ScoutTheme.colors.topBarBackground,
        useDarkIcons = false
    ) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = {
                AddGameScreen(
                    viewState = viewState,
                    libraryState = libraryState
                ) { gameStatus, platforms, notes, rating ->
                    viewModel.saveGameToLibrary(
                        gameStatus = gameStatus,
                        platforms = platforms,
                        notes = notes,
                        rating = rating
                    )
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
                viewState = viewState,
                removeGame = viewModel::removeGame,
                navigateBack = {
                    if (modalBottomSheetState.isVisible) {
                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                    } else {
                        navigateBack()
                    }
                },
                navigateToScreen = navigateToScreen,
            ) {
                scope.launch {
                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }
    }
}
