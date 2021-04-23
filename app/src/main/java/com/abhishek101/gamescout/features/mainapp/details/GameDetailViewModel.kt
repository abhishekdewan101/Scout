package com.abhishek101.gamescout.features.mainapp.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.repositories.GameRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class GameDetailViewModel(private val gameRepository: GameRepository) : ViewModel() {

    fun getGameDetails(slug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(slug).collect {
                Timber.d(it.toString())
            }
        }
    }
}