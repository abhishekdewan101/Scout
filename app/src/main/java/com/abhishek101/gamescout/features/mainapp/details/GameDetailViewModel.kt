package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.core.utils.buildImageString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class GameDetailViewModel(private val gameRepository: GameRepository) : ViewModel() {

    val gameDetails = mutableStateOf<IgdbGameDetail?>(null)
    private val libraryGameDetails = mutableStateOf<LibraryGame?>(null)

    fun getGameDetails(slug: String) {
        viewModelScope.launch {
            launch {
                gameRepository.getGameDetailForSlug(slug).collect {
                    Timber.d("Game Detail Came Back")
                    gameDetails.value = it
                }
            }
        }
    }

    fun isPlatformOwned(platform: String): Boolean {
        return if (libraryGameDetails.value != null) {
            Timber.d(libraryGameDetails.value!!.platform.toString())
            libraryGameDetails.value!!.platform.contains(platform).also {
                Timber.d("Platform $platform is owned by user $it")
            }
        } else {
            false
        }
    }
}
