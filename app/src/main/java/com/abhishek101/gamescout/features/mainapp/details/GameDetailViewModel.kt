package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(private val gameRepository: GameRepository) : ViewModel() {

    val gameDetails = mutableStateOf<IgdbGameDetail?>(null)

    fun getGameDetails(slug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(slug).collect {
                println(it)
                gameDetails.value = it
            }
        }
    }
}
