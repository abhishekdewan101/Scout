package com.abhishek101.core.viewmodels.search

import com.abhishek101.core.models.IgdbGame

sealed class SearchViewState {
    object Initial : SearchViewState()
    object Loading : SearchViewState()
    data class SearchResults(
        val results: List<IgdbGame>
    ) : SearchViewState()
}
