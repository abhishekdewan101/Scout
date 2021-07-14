package com.abhishek101.core.viewmodels.gamelist

import com.abhishek101.core.models.IgdbGame
import kotlinx.serialization.Serializable

sealed class GameListViewState {
    object Loading : GameListViewState()

    data class NonEmptyViewState(
        val headerList: GameListData,
        val otherLists: List<GameListData>
    ) : GameListViewState()
}

@Serializable
data class GameListData(
    val listType: ListType,
    val title: String,
    val games: List<IgdbGame>
)
