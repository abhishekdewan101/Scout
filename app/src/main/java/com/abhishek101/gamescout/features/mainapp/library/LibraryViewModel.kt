package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.repositories.LibraryRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class LibraryViewModel(private val libraryRepository: LibraryRepository) : ViewModel() {

    val libraryGames = mutableStateOf<List<LibraryGame>?>(null)

    init {
        viewModelScope.launch {
            libraryRepository.getAllGames().collect {
                Timber.d(it.toString())
            }
        }
    }

    fun deleteAllGames() {
        libraryRepository.clearTables()
    }
}
