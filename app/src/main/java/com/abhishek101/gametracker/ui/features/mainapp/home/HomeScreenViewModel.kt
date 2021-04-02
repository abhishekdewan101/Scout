package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.gametracker.ui.components.titledlist.TiledGridListItem
import com.abhishek101.gametracker.utils.buildImageString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val gameRepository: GameRepository) : ViewModel() {

    val carouselGameMap = mutableStateMapOf<String, String>()
    val highlyRatedGameList = mutableStateListOf<TiledGridListItem>()

    init {
        getBannerGamesPoster()
        getTopRatedGamesOfLastYear()
    }

    private fun getBannerGamesPoster() {
        viewModelScope.launch {
            gameRepository.getHeadlineBannerPosters().collect { gamePosterList ->
                carouselGameMap.putAll(
                    gamePosterList.map { it.name to buildImageString(it.screenShots[0].imageId) }
                )
            }
        }
    }

    private fun getTopRatedGamesOfLastYear() {
        viewModelScope.launch {
            gameRepository.getTopRatedGamesOfLastYear().collect { posterList ->
                highlyRatedGameList.addAll(
                    posterList.map {
                        TiledGridListItem(
                            it.name,
                            buildImageString(it.cover.imageId)
                        )
                    }
                )
            }
        }
    }
}
