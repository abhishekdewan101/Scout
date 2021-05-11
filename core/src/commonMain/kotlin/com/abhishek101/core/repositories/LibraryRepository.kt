package com.abhishek101.core.repositories

import com.abhishek101.core.db.Library
import com.abhishek101.core.models.IgdbGame
import com.abhishek101.core.utils.DatabaseHelper
import com.abhishek101.core.utils.enums.GameStatus
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface LibraryRepository {
    fun addGameToLibrary(
        slug: String,
        name: String,
        coverId: String,
        ownedPlatform: String,
        firstReleaseDate: Long
    )

    fun getLibraryGames(): Flow<List<Library>>
    fun updateGameToNowPlaying(game: IgdbGame)
    fun updateGameToCompleted(didFinish: Boolean, comments: String, rating: Int, game: IgdbGame)
}

class LibraryRepositoryImpl(private val dbHelper: DatabaseHelper) : LibraryRepository {

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

    override fun addGameToLibrary(
        slug: String,
        name: String,
        coverId: String,
        ownedPlatform: String,
        firstReleaseDate: Long
    ) {
        val now = Clock.System.now().epochSeconds
        val gameStatus = when {
            now > firstReleaseDate -> GameStatus.OWNED
            else -> GameStatus.WANT
        }
        dbHelper.libraryQueries.addGameToLibrary(
            slug,
            name,
            coverId,
            ownedPlatform,
            gameStatus,
            now
        )
    }

    override fun getLibraryGames(): Flow<List<Library>> {
        return dbHelper.libraryQueries
            .getLibraryGames()
            .asFlow()
            .mapToList()
            .flowOn(Dispatchers.Default)
    }

    override fun updateGameToNowPlaying(game: IgdbGame) {
        dbHelper.libraryQueries.updateGameAsNowPlaying(GameStatus.PLAYING, game.slug)
    }
}