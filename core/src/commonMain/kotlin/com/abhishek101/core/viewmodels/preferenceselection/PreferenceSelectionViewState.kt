package com.abhishek101.core.viewmodels.preferenceselection

import com.abhishek101.core.db.Platform

sealed class PreferenceSelectionViewState {
    object Loading : PreferenceSelectionViewState()
    data class Result(
        val platforms: List<Platform>
    ) : PreferenceSelectionViewState() {
        val ownedPlatformCount by lazy {
            platforms.filter { it.isOwned!! }.size
        }
    }
}
