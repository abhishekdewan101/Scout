package com.abhishek101.gamescout.features.mainapp.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.EmptyList
import com.abhishek101.core.models.ListData
import com.abhishek101.core.repositories.GameRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchScreenViewModel(private val gameRepository: GameRepository) : ViewModel() {
    val searchResults = mutableStateOf<ListData>(EmptyList)
    val isSearching = mutableStateOf(false)

    fun searchForGames(searchString: String) {
        isSearching.value = true
        viewModelScope.launch {
            gameRepository.searchForGames(searchString).collect {
                isSearching.value = false
                searchResults.value = it
            }
        }
    }
}
