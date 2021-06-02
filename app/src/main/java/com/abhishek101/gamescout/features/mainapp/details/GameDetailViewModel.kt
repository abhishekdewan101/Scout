package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.models.LibraryListType
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {
    private lateinit var job: Job

    var viewState by mutableStateOf<IgdbGameDetail?>(null)

    var inLibrary by mutableStateOf(false)

    fun getGameDetails(gameSlug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(gameSlug).collect {
                viewState = it
                job = launch {
                    libraryRepository.getGameForSlug(gameSlug).collect { game ->
                        inLibrary = game != null
                    }
                }
            }
        }
    }

    fun toggleGameInLibrary() {
        if (inLibrary) {
            viewState?.let {
                libraryRepository.removeGameFromLibrary(it.slug)
            }
        } else {
            viewState?.let {
                libraryRepository.insertGameIntoLibrary(
                    it.slug,
                    it.name,
                    it.cover!!.qualifiedUrl,
                    it.firstReleaseDate,
                    listOf(it.platform?.get(0)?.slug ?: ""),
                    LibraryListType.BACKLOG,
                    GameStatus.OWNED
                )
            }
        }
    }
}
