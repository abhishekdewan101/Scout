package com.abhishek101.core.repositories

import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.db.GenreQueries
import com.abhishek101.core.db.PlatformQueries
import com.abhishek101.core.models.GamePosterRemoteEntity
import com.abhishek101.core.remote.GameApi
import com.abhishek101.core.utils.QueryType.SHOWCASE
import com.abhishek101.core.utils.buildQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

interface GameRepository {
    suspend fun getHeadlineBannerPosters(): Flow<List<GamePosterRemoteEntity>>
}

class GameRepositoryImpl(
    private val gameApi: GameApi,
    private val authenticationQueries: AuthenticationQueries,
    private val genreQueries: GenreQueries,
    private val platformQueries: PlatformQueries
) : GameRepository {
    override suspend fun getHeadlineBannerPosters(): Flow<List<GamePosterRemoteEntity>> {
        val timeNow = Clock.System.now().epochSeconds
        val authentication = authenticationQueries.getAuthenticationData(timeNow).executeAsOne()

        val favoriteGenres = genreQueries.getAllFavoriteGenres().executeAsList()
        val ownedPlatforms = platformQueries.getAllFavoritePlatforms().executeAsList()

        return flow {
            emit(emptyList())
            val posterList = gameApi.getGamePostersForQuery(
                buildQuery(SHOWCASE, favoriteGenres, ownedPlatforms),
                authentication.accessToken
            )
            emit(posterList)
        }
    }
}