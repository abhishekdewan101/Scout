package com.abhishek101.core.repositories

import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.remote.GameApi
import com.abhishek101.core.utils.DatabaseHelper
import com.abhishek101.core.utils.buildGameDetailQuery
import com.abhishek101.core.utils.buildQuery
import com.abhishek101.core.utils.buildSearchQuery
import com.abhishek101.core.viewmodels.gamelist.GameListData
import com.abhishek101.core.viewmodels.gamelist.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface GameRepository {
    suspend fun getListDataForType(type: ListType): Flow<GameListData>

    suspend fun getGameDetailForSlug(slug: String): IgdbGameDetail

    suspend fun searchForGames(searchTerm: String): Flow<GameListData>
}

class GameRepositoryImpl(
    private val gameApi: GameApi,
    private val dbHelper: DatabaseHelper
) : GameRepository {
    override suspend fun getListDataForType(type: ListType): Flow<GameListData> {
        return flow {
            val posters = gameApi.getGamePostersForQuery(
                buildQuery(
                    type,
                    dbHelper.genreFilter,
                    dbHelper.platformFilter
                ),
                dbHelper.accessToken
            )
            emit(GameListData(type, type.title, posters))
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getGameDetailForSlug(slug: String): IgdbGameDetail {
        return gameApi.getGameDetailsForQuery(buildGameDetailQuery(slug), dbHelper.accessToken)
    }

    override suspend fun searchForGames(searchTerm: String): Flow<GameListData> {
        return flow {
            val posters =
                gameApi.searchGamesForQuery(buildSearchQuery(searchTerm), dbHelper.accessToken)
            emit(GameListData(ListType.SEARCH_RESULTS, ListType.SEARCH_RESULTS.title, posters))
        }.flowOn(Dispatchers.Default)
    }
}
