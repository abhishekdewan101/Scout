package com.abhishek101.core.viewmodels.library

import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.repositories.LibraryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LibraryViewModel(
    private val libraryRepository: LibraryRepository,
    private val defaultScope: CoroutineScope
) {

    private val _libraryGames = MutableStateFlow(listOf<LibraryGame>())

    private val libraryGames: StateFlow<List<LibraryGame>> = _libraryGames

    fun getLibraryGames(listener: (List<LibraryGame>) -> Unit) {
        libraryGames.onEach {
            listener(it)
        }.launchIn(defaultScope)

        libraryRepository.getAllGames().onEach {
            _libraryGames.value = it
        }.launchIn(defaultScope)
    }
}
