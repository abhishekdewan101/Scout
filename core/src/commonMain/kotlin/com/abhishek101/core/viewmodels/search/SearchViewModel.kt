package com.abhishek101.core.viewmodels.search

import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.utils.KeyValueStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val gameRepository: GameRepository,
    private val keyValueStore: KeyValueStore,
    private val defaultScope: CoroutineScope
) {
    private val _viewState: MutableStateFlow<SearchViewState> = MutableStateFlow(SearchViewState.Initial)

    private val _recentSearchState = MutableStateFlow(listOf<String>())

    private val recentSearchKey = "recent_search_key"

    val viewState: StateFlow<SearchViewState> = _viewState

    val recentSearchState: StateFlow<List<String>> = _recentSearchState

    init {
        _recentSearchState.value = getRecentSearchTerms()
    }

    fun getRecentSearches(listener: (List<String>) -> Unit) {
        recentSearchState.onEach {
            listener(it)
        }.launchIn(defaultScope)
    }

    fun searchForGame(searchTerm: String) {
        _viewState.value = SearchViewState.Loading
        setRecentSearchTerms(searchTerm)
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
        viewState.onEach {
            listener(it)
        }.launchIn(defaultScope)
        searchForGame(searchTerm = searchTerm)
    }

    fun resetSearchState() {
        _viewState.value = SearchViewState.Initial
    }

    private fun setRecentSearchTerms(searchTerm: String) {
        keyValueStore.getString(recentSearchKey).split(",").toList().apply {
            if (!contains(searchTerm.trim())) {
                val mutatedList = toMutableList()
                mutatedList.add(0, searchTerm)
                if (mutatedList.size > 5) {
                    keyValueStore.setString(recentSearchKey, mutatedList.take(5).joinToString(","))
                } else {
                    keyValueStore.setString(recentSearchKey, mutatedList.joinToString(","))
                }
            }
            _recentSearchState.value = getRecentSearchTerms()
        }
    }

    private fun getRecentSearchTerms(): List<String> {
        val recentSearchList = keyValueStore.getString(recentSearchKey)
        return if (recentSearchList.isBlank()) {
            listOf()
        } else {
            recentSearchList.split(',').toList().filter { it.isNotBlank() }
        }
    }
}
