package com.abhishek101.core.repositories

import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.db.Genre
import com.abhishek101.core.db.GenreQueries
import com.abhishek101.core.remote.GenreApi
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface GenreRepository {
    fun getCachedGenres(): Flow<List<Genre>>
    suspend fun updateCachedGenres()
    fun setGenreAsFavorite(genre: Genre, isFavorite: Boolean)
    fun getFavoriteGenres(): Flow<List<Genre>>
}

class GenreRepositoryImpl(
    private val genreApi: GenreApi,
    private val genreQueries: GenreQueries,
    private val authenticationQueries: AuthenticationQueries
) : GenreRepository {

    override fun getCachedGenres(): Flow<List<Genre>> {
        return genreQueries.getAllGenres().asFlow().mapToList().flowOn(Dispatchers.Default)
    }

    override suspend fun updateCachedGenres() {
        val timeNow = Clock.System.now().epochSeconds
        val accessToken = authenticationQueries.getAuthenticationData(timeNow).executeAsOne()
        genreApi.getGenres(accessToken.accessToken).forEach {
            genreQueries.insertGenre(it.id, it.slug, it.name)
        }
    }

    override fun setGenreAsFavorite(genre: Genre, isFavorite: Boolean) {
        genreQueries.updateFavorite(isFavorite, genre.slug)
    }

    override fun getFavoriteGenres(): Flow<List<Genre>> {
        return genreQueries.getAllFavoriteGenres().asFlow().mapToList().flowOn(Dispatchers.Default)
    }
}