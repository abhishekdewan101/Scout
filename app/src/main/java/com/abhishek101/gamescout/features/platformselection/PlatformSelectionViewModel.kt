package com.abhishek101.gamescout.features.platformselection

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

    val ownedPlatformCount = mutableStateOf(0)

    init {
        viewModelScope.launch {
            platformRepository.getCachedPlatforms().collect {
                if (it.isEmpty()) {
                    platformRepository.updateCachedPlatforms()
                    isLoading.value = true
                } else {
                    platforms.value = it
                    isLoading.value = false
                    ownedPlatformCount.value = it.filter { platform -> platform.isOwned == true }.size
                }
            }
        }
    }

    fun updateOwnedPlatform(slug: String, isOwned: Boolean) {
        platformRepository.setPlatformAsOwned(slug, isOwned)
        if (isOwned) {
            ownedPlatformCount.value++
        } else {
            ownedPlatformCount.value--
        }
    }
}
