package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.repositories.LibraryRepository

class LibraryViewModel(private val libraryRepository: LibraryRepository) : ViewModel() {

    val libraryGames = mutableStateOf<List<LibraryGame>?>(null)

    init {
        // viewModelScope.launch {
        //     libraryRepository.getGamesInLibrary().collect {
        //         libraryGames.value = it
        //     }
        // }
    }

    fun deleteAllGames() {
        libraryRepository.clearTables()
    }
}
