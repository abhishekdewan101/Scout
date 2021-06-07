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
import com.abhishek101.gamescout.components.AddGameFormData
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

    var inLibrary by mutableStateOf(false)

    var initialSaveLocation by mutableStateOf("")

    var initialOwnedStatus by mutableStateOf("")

    var initialQueueStatus by mutableStateOf("")

    fun getGameDetails(gameSlug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(gameSlug).collect { gameDetail ->
                job = launch {
                    libraryRepository.getGameForSlug(gameSlug).collect { game ->
                        inLibrary = game != null
                        if (game != null) {
                            setInitialData(game.gameStatus)
                            gameDetail.platform?.let { platforms ->
                                ownedPlatforms =
                                    platforms.map { it.name to false }.toMutableStateMap()
                            }
                            ownedPlatforms.forEach { (key, value) ->
                                ownedPlatforms[key] = game.platform.contains(key)
                            }
                        }
                        viewState = gameDetail
                    }
                }
            }
        }
    }

    fun toggleGameInLibrary(addGameFormData: AddGameFormData?) {
        if (inLibrary && addGameFormData == null) {
            viewState?.let {
                libraryRepository.removeGameFromLibrary(it.slug)
            }
        } else if (addGameFormData != null) {
            setInitialData(addGameFormData.status)
            if (inLibrary) {
                viewState?.let {
                    libraryRepository.updatePlatforms(addGameFormData.platforms, it.slug)
                    when (addGameFormData.status) {
                        GameStatus.WISHLIST -> libraryRepository.updateGameStatus(
                            GameStatus.WISHLIST,
                            it.slug
                        )
                        GameStatus.QUEUED -> libraryRepository.updateGameStatus(
                            GameStatus.QUEUED,
                            it.slug
                        )
                        GameStatus.PLAYING -> libraryRepository.updateGameAsNowPlaying(it.slug)
                        GameStatus.COMPLETED, GameStatus.ABANDONED -> libraryRepository.updateGameAsFinished(
                            addGameFormData.status,
                            0,
                            addGameFormData.comments,
                            it.slug
                        )
                    }
                }
            } else {
                addGameFormData.let { formData ->
                    viewState?.let {
                        libraryRepository.insertGameIntoLibrary(
                            it.slug,
                            it.name,
                            it.cover!!.qualifiedUrl,
                            it.firstReleaseDate,
                            formData.platforms,
                            formData.status,
                            formData.comments
                        )
                    }
                }
            }
        }
    }

    private fun setInitialData(addGameFormDataStatus: GameStatus) {
        initialSaveLocation = when {
            addGameFormDataStatus == GameStatus.WISHLIST -> "WishList"
            else -> "Library"
        }

        initialOwnedStatus = when {
            addGameFormDataStatus == GameStatus.COMPLETED -> "Game Completed"
            addGameFormDataStatus == GameStatus.ABANDONED -> "Didn't Finish"
            else -> "Queue Game"
        }

        initialQueueStatus = when {
            addGameFormDataStatus == GameStatus.PLAYING -> "Now"
            else -> "Next"
        }
    }
}
