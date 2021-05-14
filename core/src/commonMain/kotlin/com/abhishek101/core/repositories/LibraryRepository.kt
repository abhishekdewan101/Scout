package com.abhishek101.core.repositories

import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.LibraryGameStatus
import com.abhishek101.core.models.LibraryGameStatus.OWNED
import com.abhishek101.core.models.LibraryGameStatus.WISHLISTED
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface LibraryRepository {
    fun getGamesInLibrary(): Flow<List<LibraryGame>>
    fun getGameForSlug(slug: String): Flow<LibraryGame?>
    fun insertGameIntoLibrary(
        slug: String,
        name: String,
        coverUrl: String,
        releaseDate: Long,
        platform: List<String>
    )

    fun togglePlatformForGame(platform: String, slug: String)

    fun getGamesFromStatus(status: LibraryGameStatus): Flow<List<LibraryGame>>

    fun updateGameStatus(status: LibraryGameStatus, slug: String)

    fun markGameAsNowPlaying(slug: String)

    fun markGameAsFinished(status: LibraryGameStatus, rating: Long, notes: String, slug: String)
    fun clearTables()
}

class LibraryRepositoryImpl(databaseHelper: DatabaseHelper) : LibraryRepository {

    private val libraryQueries = databaseHelper.libraryGameQueries

    override fun getGamesInLibrary(): Flow<List<LibraryGame>> {
        return libraryQueries.selectAllGames().asFlow().mapToList().flowOn(Dispatchers.Default)
    }

    override fun getGameForSlug(slug: String): Flow<LibraryGame?> {
        return libraryQueries.getGameForSlug(slug).asFlow().mapToOneOrNull()
            .flowOn(Dispatchers.Default)
    }

    override fun insertGameIntoLibrary(
        slug: String,
        name: String,
        coverUrl: String,
        releaseDate: Long,
        platform: List<String>
    ) {
        libraryQueries.insertGameIntoLibrary(
            slug,
            name,
            coverUrl,
            releaseDate,
            getGameStatus(releaseDate),
            platform
        )
    }

    override fun togglePlatformForGame(platform: String, slug: String) {
        val ownedPlatforms = libraryQueries.getGameForSlug(slug).executeAsOne().platform
        val updatedPlatforms = ownedPlatforms.toMutableList().apply {
            if (contains(platform)) {
                remove(platform)
            } else {
                add(platform)
            }
        }
        if (updatedPlatforms.isEmpty()) {
            libraryQueries.removeGameFromLibrary(slug)
        } else {
            libraryQueries.updateOwnedPlatform(updatedPlatforms, slug)
        }
    }

    override fun getGamesFromStatus(status: LibraryGameStatus): Flow<List<LibraryGame>> {
        return libraryQueries.selectGamesForStatus(status).asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }

    override fun updateGameStatus(status: LibraryGameStatus, slug: String) {
        libraryQueries.updateGameStatus(status, slug)
    }

    override fun markGameAsNowPlaying(slug: String) {
        val now = Clock.System.now().epochSeconds
        libraryQueries.updateGameToNowPlaying(now, slug)
    }

    override fun markGameAsFinished(
        status: LibraryGameStatus,
        rating: Long,
        notes: String,
        slug: String
    ) {
        val now = Clock.System.now().epochSeconds
        libraryQueries.updateGameAsFinished(status, now, rating, notes, slug)
    }

    override fun clearTables() {
        libraryQueries.removeAllGamesFromLibrary()
    }

    private fun getGameStatus(releaseDate: Long): LibraryGameStatus {
        val now = Clock.System.now().epochSeconds
        return if (releaseDate > now) {
            WISHLISTED
        } else {
            OWNED
        }
    }
}
