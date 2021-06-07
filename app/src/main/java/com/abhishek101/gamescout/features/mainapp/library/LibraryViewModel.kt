package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.GameStatus.WISHLIST
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.ABANDONED
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.ALL
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.COMPLETED
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.PLAYING
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.QUEUED
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.WANTED
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

enum class LibraryFilters {
    ALL,
    QUEUED,
    PLAYING,
    WANTED,
    COMPLETED,
    ABANDONED
}

class LibraryViewModel(private val libraryRepository: LibraryRepository) : ViewModel() {

    var libraryGames by mutableStateOf(listOf<LibraryGame>())

    private var allGames = listOf<LibraryGame>()

    init {
        viewModelScope.launch {
            libraryRepository.getAllGames().collect {
                allGames = it
                libraryGames = allGames
                Timber.d(libraryGames.toString())
            }
        }
    }

    fun updateGamesForFilter(filter: LibraryFilters) {
        libraryGames = when (filter) {
            ALL -> allGames
            PLAYING -> allGames.filter { it.gameStatus == GameStatus.PLAYING }
            QUEUED -> allGames.filter { it.gameStatus == GameStatus.QUEUED }
            WANTED -> allGames.filter { it.gameStatus == WISHLIST }
            COMPLETED -> allGames.filter { it.gameStatus == GameStatus.COMPLETED }
            ABANDONED -> allGames.filter { it.gameStatus == GameStatus.ABANDONED }
        }
    }

    fun deleteAllGames() {
        libraryRepository.clearTables()
    }
}
