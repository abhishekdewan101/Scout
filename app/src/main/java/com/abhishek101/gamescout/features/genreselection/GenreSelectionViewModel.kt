package com.abhishek101.gamescout.features.genreselection

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.db.Genre
import com.abhishek101.core.repositories.GenreRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val genreEmojiMap = mapOf(
    "point-and-click" to "🖱",
    "fighting" to "🤜 🤛",
    "shooter" to "🔫",
    "music" to "🎤🎸",
    "platform" to "🧗",
    "puzzle" to "🧩",
    "racing" to "🏎",
    "role-playing-rpg" to "👮👨‍🍳‍",
    "real-time-strategy-rts" to "🤔",
    "moba" to "🖥😱",
    "card-and-board-game" to "🃏",
    "visual-novel" to "📚👀",
    "arcade" to "🕹",
    "indie" to "😍🕹",
    "adventure" to "🤠",
    "pinball" to "👾",
    "quiz-trivia" to "🧐",
    "hack-and-slash-beat-em-up" to "⚔️",
    "tactical" to "🎲",
    "turn-based-strategy-tbs" to "🙋🙋‍‍",
    "strategy" to "♟♗",
    "sport" to "⚽️",
    "simulator" to "📺"
)

class GenreSelectionViewModel(private val genreRepository: GenreRepository) : ViewModel() {
    val genres = mutableStateOf(listOf<Genre>())
    val isLoading = mutableStateOf(true)

    init {
        viewModelScope.launch {
            genreRepository.getCachedGenres().collect {
                if (it.isEmpty()) {
                    genreRepository.updateCachedGenres()
                    isLoading.value = true
                } else {
                    genres.value = it
                    isLoading.value = false
                }
            }
        }
    }

    fun updateGenreAsFavorite(genre: Genre, isFavorite: Boolean) {
        genreRepository.setGenreAsFavorite(genre = genre, isFavorite = isFavorite)
    }

    fun getFavoriteGenreCount(): Int {
        var favoriteGenreCount = 0
        genres.value.forEach {
            if (it.isFavorite == true) favoriteGenreCount++
        }
        return favoriteGenreCount
    }
}
