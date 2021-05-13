package com.abhishek101.core.repositories

import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.LibraryGameStatus
import com.abhishek101.core.models.LibraryGameStatus.OWNED
import com.abhishek101.core.models.LibraryGameStatus.PLAYING
import com.abhishek101.core.models.LibraryGameStatus.WISHLISTED
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface LibraryRepository {
    fun getLibraryGames(): Flow<List<LibraryGame>>
    fun getLibraryGameForSlug(slug: String): Flow<List<LibraryGame>>
    fun addGameToLibrary(
        slug: String,
        name: String,
        coverId: String,
        releaseDate: Long,
        platform: String
    )

    fun getGamesForStatus(status: LibraryGameStatus): Flow<List<LibraryGame>?>
    fun setGameAsNowPlaying(slug: String)
    fun setGamesAsFinished(
        slug: String,
        status: LibraryGameStatus,
        userRating: Int,
        userNotes: String
    )
}

class LibraryRepositoryImpl(private val databaseHelper: DatabaseHelper) : LibraryRepository {
    override fun getLibraryGames(): Flow<List<LibraryGame>> {
        return databaseHelper.libraryGameQueries.getLibraryGames()
            .asFlow()
            .mapToList()
            .flowOn(
                Dispatchers.Default
            )
    }

    override fun getLibraryGameForSlug(slug: String): Flow<List<LibraryGame>> {
        return databaseHelper.libraryGameQueries.getLibraryGameForSlug(slug).asFlow()
            .mapToList().flowOn(Dispatchers.Default)
    }

    override fun addGameToLibrary(
        slug: String,
        name: String,
        coverId: String,
        releaseDate: Long,
        platform: String
    ) {
        databaseHelper.libraryGameQueries.addGameToLibrary(
            slug,
            name,
            coverId,
            releaseDate,
            getGameStatus(releaseDate),
            platform
        )
    }

    override fun getGamesForStatus(status: LibraryGameStatus): Flow<List<LibraryGame>?> {
        return databaseHelper.libraryGameQueries.getGamesForStatus(status)
            .asFlow()
            .mapToList()
            .flowOn(Dispatchers.Default)
    }

    override fun setGameAsNowPlaying(slug: String) {
        val now = Clock.System.now().epochSeconds
        databaseHelper.libraryGameQueries.setGameAsNowPlaying(PLAYING, now, slug)
    }

    override fun setGamesAsFinished(
        slug: String,
        status: LibraryGameStatus,
        userRating: Int,
        userNotes: String
    ) {
        val now = Clock.System.now().epochSeconds
        databaseHelper.libraryGameQueries.setGameAsFinished(
            status,
            now,
            userRating.toLong(),
            userNotes,
            slug
        )
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
