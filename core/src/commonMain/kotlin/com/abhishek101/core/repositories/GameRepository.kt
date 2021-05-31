package com.abhishek101.core.repositories

import com.abhishek101.core.models.GameListData
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.models.ListData
import com.abhishek101.core.remote.GameApi
import com.abhishek101.core.utils.DatabaseHelper
import com.abhishek101.core.utils.buildGameDetailQuery
import com.abhishek101.core.utils.buildQuery
import com.abhishek101.core.utils.buildSearchQuery
import com.abhishek101.core.viewstates.GameDetailViewState
import com.abhishek101.core.viewstates.toViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

enum class ListType(val title: String) {
    SHOWCASE("Showcase"),
    TOP_RATED("Top Rated"),
    COMING_SOON("Coming Soon"),
    RECENT("Recent"),
    MOST_HYPED("Most Hyped")
}

interface GameRepository {
    suspend fun getListDataForType(type: ListType): Flow<ListData>

    suspend fun getGameDetailForSlug(slug: String): Flow<GameDetailViewState>

    suspend fun searchForGames(searchTerm: String): Flow<ListData>
}

class GameRepositoryImpl(
    private val gameApi: GameApi,
    private val dbHelper: DatabaseHelper
) : GameRepository {
    override suspend fun getListDataForType(type: ListType): Flow<ListData> {
        return flow {
            val posters = gameApi.getGamePostersForQuery(
                buildQuery(
                    type,
                    dbHelper.genreFilter,
                    dbHelper.platformFilter
                ),
                dbHelper.accessToken
            )
            emit(GameListData(type.title, posters))
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getGameDetailForSlug(slug: String): Flow<GameDetailViewState> {
        return flow {
            val gameDetail =
                gameApi.getGameDetailsForQuery(buildGameDetailQuery(slug), dbHelper.accessToken)
            emit(gameDetail.toViewState())
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun searchForGames(searchTerm: String): Flow<ListData> {
        return flow {
            val posters =
                gameApi.searchGamesForQuery(buildSearchQuery(searchTerm), dbHelper.accessToken)
            emit(GameListData("Search Results", posters))
        }.flowOn(Dispatchers.Default)
    }
}
