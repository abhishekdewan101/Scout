package com.abhishek101.core.viewmodels.preferenceselection

import com.abhishek101.core.repositories.PlatformRepository
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState.Loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PreferenceSelectionViewModel(
    private val platformRepository: PlatformRepository,
    private val defaultScope: CoroutineScope,
) {
    private val _viewState: MutableStateFlow<PreferenceSelectionViewState> = MutableStateFlow(Loading)

    val viewState: StateFlow<PreferenceSelectionViewState> = _viewState

    fun getPlatforms() {
        defaultScope.launch {
            platformRepository.getCachedPlatforms().collect {
                if (it.isEmpty()) {
                    platformRepository.updateCachedPlatforms()
                } else {
                    _viewState.value = PreferenceSelectionViewState.Result(
                        platforms = it
                    )
                }
            }
        }
    }

    fun getPlatforms(listener: (PreferenceSelectionViewState) -> Unit) {
        listener(Loading)
        defaultScope.launch {
            viewState.onEach {
                listener(it)
            }.collect()
        }
        getPlatforms()
    }

    fun togglePlatform(platformSlug: String, isOwned: Boolean) {
        platformRepository.setPlatformAsOwned(
            slug = platformSlug,
            isOwned = isOwned
        )
    }
}
