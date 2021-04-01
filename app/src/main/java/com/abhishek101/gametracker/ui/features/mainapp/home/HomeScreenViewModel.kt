package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.GamePosterRemoteEntity
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.gametracker.ui.features.mainapp.home.BottomNavigationState.GAME_LIST
import com.abhishek101.gametracker.utils.buildImageString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

enum class BottomNavigationState {
    GAME_LIST,
    SEARCH
}

class HomeScreenViewModel(private val gameRepository: GameRepository) : ViewModel() {

    val bottomSelected = mutableStateOf(GAME_LIST)
    val carouselGameMap = mutableStateMapOf<String, String>()
    val topRatedGameList = mutableStateOf(listOf<GamePosterRemoteEntity>())

    init {
        getBannerGamesPoster()
        // getTopRatedGamesOfLastYear()
    }

    fun updateBottomSelectedState(bottomNavigationState: BottomNavigationState) {
        bottomSelected.value = bottomNavigationState
    }

    private fun getBannerGamesPoster() {
        viewModelScope.launch {
            gameRepository.getHeadlineBannerPosters().collect { gamePosterList ->
                carouselGameMap.putAll(
                    gamePosterList.map { it.name to buildImageString(it.screenShots[0].imageId) }
                        .toMap()
                )
            }
        }
    }

    private fun getTopRatedGamesOfLastYear() {
        viewModelScope.launch {
            gameRepository.getTopRatedGamesOfLastYear().collect {
                topRatedGameList.value = it
            }
        }
    }
}
