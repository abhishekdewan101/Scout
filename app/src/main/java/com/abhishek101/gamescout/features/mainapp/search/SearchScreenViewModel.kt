package com.abhishek101.gamescout.features.mainapp.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.viewmodels.gamelist.GameListData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchScreenViewModel(private val gameRepository: GameRepository) : ViewModel() {
    val searchResults = mutableStateOf<GameListData?>(null)
    val isSearching = mutableStateOf(false)
    private val searchTerm = mutableStateOf("")

    fun searchForGames(searchString: String) {
        searchTerm.value = searchString
        isSearching.value = true
        viewModelScope.launch {
            gameRepository.searchForGames(searchString).collect {
                isSearching.value = false
                searchResults.value = it
                Timber.d("Search results - ${(it as? GameListData)?.games?.size} ")
            }
        }
    }
}
