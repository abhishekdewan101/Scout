package com.abhishek101.core.repositories

import com.abhishek101.core.models.IgdbGame
import com.abhishek101.core.models.IgdbPlatform
import com.abhishek101.core.utils.DatabaseHelper
import com.abhishek101.core.utils.enums.GameStatus
import kotlinx.datetime.Clock

interface LibraryRepository {
    fun addGameToLibrary(game: IgdbGame, ownedPlatform: IgdbPlatform)
    fun updateGameToNowPlaying(game: IgdbGame)
    fun updateGameToCompleted(didFinish: Boolean, comments: String, rating: Int, game: IgdbGame)
}

class LibraryRepositoryImpl(private val dbHelper: DatabaseHelper) : LibraryRepository {
    override fun addGameToLibrary(game: IgdbGame, ownedPlatform: IgdbPlatform) {
        val now = Clock.System.now().epochSeconds
        val gameStatus = when {
            now > game.firstReleaseDate -> GameStatus.OWNED
            else -> GameStatus.WANT
        }
        dbHelper.libraryQueries.addGameToLibrary(
            game.slug,
            game.name,
            game.cover!!.imageId, // we already filter games without covers before showing them to user
            ownedPlatform.name,
            gameStatus,
            now
        )
    }

    override fun updateGameToCompleted(
        didFinish: Boolean,
        comments: String,
        rating: Int,
        game: IgdbGame
    ) {
        val status = if (didFinish) GameStatus.FINISHED else GameStatus.LEFT
        val now = Clock.System.now().epochSeconds
        dbHelper.libraryQueries.updateGameAsCompleted(
            status,
            now,
            rating.toLong(),
            comments,
            game.slug
        )
    }

    override fun updateGameToNowPlaying(game: IgdbGame) {
        dbHelper.libraryQueries.updateGameAsNowPlaying(GameStatus.PLAYING, game.slug)
    }
}