package com.abhishek101.gametracker.ui.features.home

import androidx.compose.runtime.mutableStateOf
import com.abhishek101.gametracker.ui.features.home.BottomNavigationState.GAME_LIST

enum class BottomNavigationState {
    GAME_LIST,
    SEARCH
}

class HomeScreenViewModel {
    val bottomSelected = mutableStateOf(GAME_LIST)

    fun updateBottomSelectedState(bottomNavigationState: BottomNavigationState) {
        bottomSelected.value = bottomNavigationState
    }
}