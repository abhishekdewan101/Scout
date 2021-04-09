package com.abhishek101.core.repositories

import com.abhishek101.core.models.GameList
import com.abhishek101.core.remote.GameApi
import com.abhishek101.core.utils.DatabaseHelper
import com.abhishek101.core.utils.buildQuery
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
    suspend fun getListDataForType(type: ListType): Flow<GameList>
}

class GameRepositoryImpl(
    private val gameApi: GameApi,
    private val dbHelper: DatabaseHelper
) : GameRepository {
    override suspend fun getListDataForType(type: ListType): Flow<GameList> {
        return flow {
            val poster = gameApi.getGamePostersForQuery(
                buildQuery(
                    type,
                    dbHelper.genreFilter,
                    dbHelper.platformFilter
                ),
                dbHelper.accessToken
            )
            emit(GameList(type.title, poster))
        }.flowOn(Dispatchers.Default)
    }
}
