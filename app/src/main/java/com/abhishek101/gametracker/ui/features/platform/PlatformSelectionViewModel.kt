package com.abhishek101.gametracker.ui.features.platform

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.Platforms
import com.abhishek101.core.repositories.PlatformRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class PlatformSelectionViewModel(private val platformRepository: PlatformRepository) : ViewModel() {

    val platforms: MutableState<List<Platforms>> = mutableStateOf(listOf())

    val isLoading = mutableStateOf(true)

    fun getPlatforms() {
        viewModelScope.launch {
            platformRepository.getPlatforms().collect {
                if (it.isEmpty()) {
                    platformRepository.getPlatformsAndUpdate()
                }
                Timber.d("List of platforms - $it")
                platforms.value = it
                isLoading.value = false
            }
        }
    }

    fun setFavoritePlatform(platform: Platforms, isFavorite: Boolean) {
        viewModelScope.launch {
            platformRepository.updateFavoritePlatform(platform = platform, isFavorite = isFavorite)
        }
    }
}