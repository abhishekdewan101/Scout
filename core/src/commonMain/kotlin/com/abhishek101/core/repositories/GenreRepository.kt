package com.abhishek101.core.repositories

import com.abhishek101.core.db.Genre
import com.abhishek101.core.remote.GenreApi
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface GenreRepository {
    fun getCachedGenres(): Flow<List<Genre>>
    suspend fun updateCachedGenres()
    fun setGenreAsFavorite(slug: String, isFavorite: Boolean)
    fun getFavoriteGenres(): Flow<List<Genre>>
}

class GenreRepositoryImpl(
    private val genreApi: GenreApi,
    private val dbHelper: DatabaseHelper
) : GenreRepository {

    private val genreQueries = dbHelper.genreQueries

    override fun getCachedGenres(): Flow<List<Genre>> {
        return genreQueries.getAllGenres().asFlow().mapToList().flowOn(Dispatchers.Default)
    }

    override suspend fun updateCachedGenres() {
        genreApi.getGenres(dbHelper.accessToken).forEach {
            genreQueries.insertGenre(it.id, it.slug, it.name)
        }
    }

    override fun setGenreAsFavorite(slug: String, isFavorite: Boolean) {
        genreQueries.updateFavorite(isFavorite, slug)
    }

    override fun getFavoriteGenres(): Flow<List<Genre>> {
        return genreQueries.getAllFavoriteGenres().asFlow().mapToList().flowOn(Dispatchers.Default)
    }
}
