package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.viewstates.GameDetailViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(private val gameRepository: GameRepository) : ViewModel() {
    var viewState by mutableStateOf<GameDetailViewState?>(null)

    fun getGameDetails(gameSlug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(gameSlug).collect {
                viewState = it
            }
        }
    }
}
