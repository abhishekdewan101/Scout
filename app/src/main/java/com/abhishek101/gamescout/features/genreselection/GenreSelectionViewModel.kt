package com.abhishek101.gamescout.features.genreselection

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.Genre
import com.abhishek101.core.repositories.GenreRepository
import com.abhishek101.gamescout.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val genreImageMap = mapOf(
    "point-and-click" to R.drawable.point_click,
    "fighting" to R.drawable.fighting,
    "shooter" to R.drawable.shooter,
    "music" to R.drawable.music,
    "platform" to R.drawable.platformer,
    "puzzle" to R.drawable.puzzle,
    "racing" to R.drawable.racing,
    "role-playing-rpg" to R.drawable.role_playing_game,
    "real-time-strategy-rts" to R.drawable.rts,
    "moba" to R.drawable.moba,
    "card-and-board-game" to R.drawable.card_board_games,
    "visual-novel" to R.drawable.visual_novel,
    "arcade" to R.drawable.ic_arcade,
    "indie" to R.drawable.indie_games,
    "adventure" to R.drawable.ic_adventure,
    "pinball" to R.drawable.pinball,
    "quiz-trivia" to R.drawable.quiz_trivia,
    "hack-and-slash-beat-em-up" to R.drawable.hack_slash,
    "tactical" to R.drawable.tactical,
    "turn-based-strategy-tbs" to R.drawable.turn_based,
    "strategy" to R.drawable.stratergy,
    "sport" to R.drawable.sports,
    "simulator" to R.drawable.simulator
)

class GenreSelectionViewModel(private val genreRepository: GenreRepository) : ViewModel() {
    val genres = mutableStateOf(listOf<Genre>())
    val isLoading = mutableStateOf(true)
    val favoriteGenreCount = mutableStateOf(0)

    init {
        viewModelScope.launch {
            genreRepository.getCachedGenres().collect {
                if (it.isEmpty()) {
                    genreRepository.updateCachedGenres()
                    isLoading.value = true
                } else {
                    genres.value = it
                    isLoading.value = false
                    favoriteGenreCount.value = it.filter { genre -> genre.isFavorite == true }.size
                }
            }
        }
    }

    fun updateGenreAsFavorite(slug: String, isFavorite: Boolean) {
        genreRepository.setGenreAsFavorite(slug = slug, isFavorite = isFavorite)
        if (isFavorite) {
            favoriteGenreCount.value++
        } else {
            favoriteGenreCount.value--
        }
    }
}
