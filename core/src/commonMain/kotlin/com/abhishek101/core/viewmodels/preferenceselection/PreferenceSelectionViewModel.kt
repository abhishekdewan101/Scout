package com.abhishek101.core.viewmodels.preferenceselection

import com.abhishek101.core.repositories.GenreRepository
import com.abhishek101.core.repositories.PlatformRepository
import com.abhishek101.core.utils.KeyValueStore
import com.abhishek101.core.viewmodels.authentication.ON_BOARDING_COMPLETE_KEY
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState.Loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PreferenceSelectionViewModel(
    private val platformRepository: PlatformRepository,
    private val genreRepository: GenreRepository,
    private val keyValueStore: KeyValueStore,
    private val defaultScope: CoroutineScope,
) {
    private val _viewState: MutableStateFlow<PreferenceSelectionViewState> = MutableStateFlow(Loading)

    val viewState: StateFlow<PreferenceSelectionViewState> = _viewState

    fun getPlatforms() {
        platformRepository.getCachedPlatforms().onEach {
            if (it.isEmpty()) {
                defaultScope.launch {
                    platformRepository.updateCachedPlatforms()
                }
            } else {
                _viewState.value = PreferenceSelectionViewState.Result(
                    platforms = it
                )
            }
        }.launchIn(defaultScope)
    }

    fun getGenres() {
        genreRepository.getCachedGenres().onEach {
            if (it.isEmpty()) {
                defaultScope.launch {
                    genreRepository.updateCachedGenres()
                }
            } else {
                _viewState.value = PreferenceSelectionViewState.Result(
                    genres = it
                )
            }
        }.launchIn(defaultScope)
    }

    fun getGenres(listener: (PreferenceSelectionViewState) -> Unit) {
        listener(Loading)
        defaultScope.launch {
            viewState.onEach {
                listener(it)
            }.collect()
        }
        getGenres()
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

    fun setOnBoardingCompleted() {
        keyValueStore.setBoolean(ON_BOARDING_COMPLETE_KEY, true)
    }

    fun toggleGenre(genreSlug: String, isFavorite: Boolean) {
        genreRepository.setGenreAsFavorite(
            slug = genreSlug,
            isFavorite = isFavorite
        )
    }

    fun togglePlatform(platformSlug: String, isOwned: Boolean) {
        platformRepository.setPlatformAsOwned(
            slug = platformSlug,
            isOwned = isOwned
        )
    }
}
