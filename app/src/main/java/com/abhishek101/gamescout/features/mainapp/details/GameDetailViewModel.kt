package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.core.utils.buildImageString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class GameDetailViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    val gameDetails = mutableStateOf<IgdbGameDetail?>(null)
    val libraryGameDetails = mutableStateOf<LibraryGame?>(null)

    fun getGameDetails(slug: String) {
        viewModelScope.launch {
            launch {
                libraryRepository.getGameForSlug(slug).collect {
                    libraryGameDetails.value = it
                }
            }

            launch {
                gameRepository.getGameDetailForSlug(slug).collect {
                    Timber.d("Game Detail Came Back")
                    gameDetails.value = it
                }
            }
        }
    }

    fun updatePlatformAsOwned(slug: String, platform: String) {
        if (libraryGameDetails.value != null) {
            libraryRepository.togglePlatformForGame(platform, slug)
        } else {
            libraryRepository.insertGameIntoLibrary(
                gameDetails.value!!.slug,
                gameDetails.value!!.name,
                buildImageString(gameDetails.value!!.cover!!.imageId),
                gameDetails.value!!.firstReleaseDate,
                listOf(platform)
            )
        }
    }
}
