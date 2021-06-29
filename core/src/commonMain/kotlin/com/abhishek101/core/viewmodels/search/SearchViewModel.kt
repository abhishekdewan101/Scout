package com.abhishek101.core.viewmodels.search

import com.abhishek101.core.repositories.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(private val gameRepository: GameRepository, private val defaultScope: CoroutineScope) {
    private val _viewState: MutableStateFlow<SearchViewState> = MutableStateFlow(SearchViewState.Loading)

    val viewState: StateFlow<SearchViewState> = _viewState

    fun searchForGame(searchTerm: String) {
        defaultScope.launch {
            gameRepository.searchForGames(searchTerm = searchTerm).collect {
                val filteredList = it.games.filter { game -> game.cover != null }
                _viewState.value = SearchViewState.SearchResults(
                    results = filteredList
                )
            }
        }
    }

    fun searchForGame(searchTerm: String, listener: (SearchViewState) -> Unit) {
        listener(SearchViewState.Loading)
        viewState.onEach {
            listener(it)
        }.launchIn(defaultScope)
        searchForGame(searchTerm = searchTerm)
    }
}
