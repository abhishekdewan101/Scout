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
            launch {
                libraryRepository.getGameForSlug(slug).collect {
                    Timber.d((it != null).toString())
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
}
