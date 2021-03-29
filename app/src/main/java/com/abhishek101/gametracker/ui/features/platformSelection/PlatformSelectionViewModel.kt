package com.abhishek101.gametracker.ui.features.platformSelection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.Platform
import com.abhishek101.core.repositories.PlatformRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlatformSelectionViewModel(private val platformRepository: PlatformRepository) : ViewModel() {

    val platforms: MutableState<List<Platform>> = mutableStateOf(listOf())

    val isLoading = mutableStateOf(true)

    init {
        viewModelScope.launch {
            platformRepository.getCachedPlatforms().collect {
                if (it.isEmpty()) {
                    platformRepository.updateCachedPlatforms()
                    isLoading.value = true
                } else {
                    platforms.value = it
                    isLoading.value = false
                }
            }
        }
    }

    fun getOwnedPlatformCount(): Int {
        var totalOwned = 0
        platforms.value.forEach {
            if (it.isOwned == true) totalOwned++
        }
        return totalOwned
    }

    fun updateOwnedPlatform(platform: Platform, isOwned: Boolean) {
        platformRepository.setPlatformAsOwned(platform, isOwned)
    }
}
