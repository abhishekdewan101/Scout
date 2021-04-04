package com.abhishek101.core.repositories

import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.db.GenreQueries
import com.abhishek101.core.db.PlatformQueries
import com.abhishek101.core.models.GamePosterRemoteEntity
import com.abhishek101.core.remote.GameApi
import com.abhishek101.core.utils.QueryType.COMING_SOON
import com.abhishek101.core.utils.QueryType.SHOWCASE
import com.abhishek101.core.utils.QueryType.TOP_RATED_LAST_YEAR
import com.abhishek101.core.utils.QueryType.TOP_RATED_SINGLE_PLAYER
import com.abhishek101.core.utils.buildQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface GameRepository {
    suspend fun getHeadlineBannerPosters(): Flow<List<GamePosterRemoteEntity>>
    suspend fun getTopRatedGamesOfLastYear(): Flow<List<GamePosterRemoteEntity>>
    suspend fun getComingSoonGames(): Flow<List<GamePosterRemoteEntity>>
    suspend fun getSinglePlayerGames(): Flow<List<GamePosterRemoteEntity>>
}

class GameRepositoryImpl(
    private val gameApi: GameApi,
    authenticationQueries: AuthenticationQueries,
    genreQueries: GenreQueries,
    platformQueries: PlatformQueries
) : GameRepository {
    private val timeNow = Clock.System.now().epochSeconds
    private val authentication = authenticationQueries.getAuthenticationData(timeNow).executeAsOne()

    private val favoriteGenres = genreQueries.getAllFavoriteGenres().executeAsList()
    private val ownedPlatforms = platformQueries.getAllFavoritePlatforms().executeAsList()

    override suspend fun getHeadlineBannerPosters(): Flow<List<GamePosterRemoteEntity>> {
        return flow {
            emit(emptyList())
            val posterList = gameApi.getGamePostersForQuery(
                buildQuery(SHOWCASE, favoriteGenres, ownedPlatforms),
                authentication.accessToken
            )
            emit(posterList)
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getTopRatedGamesOfLastYear(): Flow<List<GamePosterRemoteEntity>> {
        return flow {
            emit(emptyList())
            val posterList = gameApi.getGamePostersForQuery(
                buildQuery(TOP_RATED_LAST_YEAR, favoriteGenres, ownedPlatforms),
                authentication.accessToken
            )
            emit(posterList)
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getComingSoonGames(): Flow<List<GamePosterRemoteEntity>> {
        return flow {
            emit(emptyList())
            val posterList = gameApi.getGamePostersForQuery(
                buildQuery(COMING_SOON, favoriteGenres, ownedPlatforms),
                authentication.accessToken
            )
            emit(posterList)
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getSinglePlayerGames(): Flow<List<GamePosterRemoteEntity>> {
        return flow {
            emit(emptyList())
            val posterList = gameApi.getGamePostersForQuery(
                buildQuery(TOP_RATED_SINGLE_PLAYER, favoriteGenres, ownedPlatforms),
                authentication.accessToken
            )
            emit(posterList)
        }.flowOn(Dispatchers.Default)
    }
}
