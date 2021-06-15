package com.abhishek101.core.repositories

import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

interface LibraryRepository {
    fun getAllGames(): Flow<List<LibraryGame>>
    fun getGameForSlug(slug: String): Flow<LibraryGame?>
    fun insertGameIntoLibrary(
        slug: String,
        name: String,
        coverUrl: String,
        releaseDate: Long,
        platform: List<String>,
        gameStatus: GameStatus,
        notes: String?
    )

    fun getGamesWithStatus(status: GameStatus): List<LibraryGame>
    fun updateGameStatus(status: GameStatus, slug: String)
    fun updateGame(gameStatus: GameStatus, platform: List<String>, notes: String, slug: String)
    fun updateGameAsNowPlaying(slug: String)
    fun updateGameAsFinished(status: GameStatus, rating: Long, notes: String?, slug: String)
    fun clearTables()
    fun getWantedGames(): Flow<List<LibraryGame>>
    fun removeGameFromLibrary(slug: String)
}

class LibraryRepositoryImpl(databaseHelper: DatabaseHelper, private val clock: Clock) :
    LibraryRepository {

    private val libraryQueries = databaseHelper.libraryGameQueries

    override fun getAllGames(): Flow<List<LibraryGame>> {
        return libraryQueries.selectAllGames().asFlow().mapToList()
    }

    override fun getGameForSlug(slug: String): Flow<LibraryGame?> {
        return libraryQueries.getGameForSlug(slug).asFlow().mapToOneOrNull()
    }

    override fun insertGameIntoLibrary(
        slug: String,
        name: String,
        coverUrl: String,
        releaseDate: Long,
        platform: List<String>,
        gameStatus: GameStatus,
        notes: String?
    ) {
        libraryQueries.insertGameIntoLibrary(
            slug,
            name,
            coverUrl,
            releaseDate,
            gameStatus,
            platform,
            notes
        )
    }

    override fun getGamesWithStatus(status: GameStatus): List<LibraryGame> {
        return libraryQueries.getGameWithStatus(status).executeAsList()
    }

    override fun updateGameStatus(status: GameStatus, slug: String) {
        libraryQueries.updateGameStatus(status, slug)
    }

    override fun updateGame(gameStatus: GameStatus, platform: List<String>, notes: String, slug: String) {
        libraryQueries.updateGameInLibrary(platform, gameStatus, notes, slug)
    }

    override fun updateGameAsNowPlaying(slug: String) {
        updateGameStatus(GameStatus.PLAYING, slug)
    }

    override fun updateGameAsFinished(
        status: GameStatus,
        rating: Long,
        notes: String?,
        slug: String
    ) {
        libraryQueries.updateGameAsFinished(status, clock.now().epochSeconds, rating, notes, slug)
    }

    override fun clearTables() {
        libraryQueries.removeAllGamesFromLibrary()
    }

    override fun getWantedGames(): Flow<List<LibraryGame>> {
        return libraryQueries.getGameWithStatus(GameStatus.WISHLIST).asFlow().mapToList()
    }

    override fun removeGameFromLibrary(slug: String) {
        libraryQueries.removeGameFromLibrary(slug)
    }
}
