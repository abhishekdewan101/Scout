package com.abhishek101.core.viewmodels.preferenceselection

import com.abhishek101.core.db.Genre
import com.abhishek101.core.db.Platform

sealed class PreferenceSelectionViewState {
    object Loading : PreferenceSelectionViewState()
    data class Result(
        val platforms: List<Platform> = listOf(),
        val genres: List<Genre> = listOf()
    ) : PreferenceSelectionViewState() {
        val ownedPlatformCount by lazy {
            platforms.filter { it.isOwned!! }.size
        }
        val ownedGenreCount by lazy {
            genres.filter { it.isFavorite!! }.size
        }
    }
}
