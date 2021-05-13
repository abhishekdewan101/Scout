package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class GameDetailViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    val gameDetails = mutableStateOf<IgdbGameDetail?>(null)
    val gameInLibrary = mutableStateOf(false)

    fun getGameDetails(slug: String) {
        viewModelScope.launch {
            gameRepository.getGameDetailForSlug(slug).collect {
                Timber.d("Game Detail Came Back")
                gameDetails.value = it
            }
            libraryRepository.getLibraryGameForSlug(slug).collect {
                gameInLibrary.value = it.isNotEmpty()
            }
        }
    }

    fun addGameToLibrary(igdbGameDetail: IgdbGameDetail, platform: String) {
        libraryRepository.addGameToLibrary(
            igdbGameDetail.slug,
            igdbGameDetail.name,
            igdbGameDetail.cover!!.imageId,
            igdbGameDetail.firstReleaseDate,
            platform
        )
    }
}
