package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository,
) : ViewModel() {
    private lateinit var job: Job

    var viewState by mutableStateOf<IgdbGameDetail?>(null)

    var ownedPlatforms by mutableStateOf(mutableMapOf<String, Boolean>())

    var gameStatus by mutableStateOf(GameStatus.OWNED)

    var inLibrary by mutableStateOf(false)

    fun getGameDetails(gameSlug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(gameSlug).collect {
                viewState = it
                job = launch {
                    libraryRepository.getGameForSlug(gameSlug).collect { game ->
                        inLibrary = game != null
                        it.platform?.let { platforms ->
                            if (game == null) {
                                ownedPlatforms =
                                    platforms.map { it.name to false }.toMutableStateMap()
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateGameStatus(gameStatus: GameStatus) {
        this.gameStatus = gameStatus
    }

    fun updatePlatformAsOwned(platform: String) {
        val owned = ownedPlatforms[platform]!!
        ownedPlatforms[platform] = !owned
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
                    ownedPlatforms.filter { it.value }.map { it.key }.toList(),
                    gameStatus
                )
            }
        }
    }
}
