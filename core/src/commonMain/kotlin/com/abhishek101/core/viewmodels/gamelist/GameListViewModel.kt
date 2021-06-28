package com.abhishek101.core.viewmodels.gamelist

import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.viewmodels.gamelist.ListType.COMING_SOON
import com.abhishek101.core.viewmodels.gamelist.ListType.MOST_HYPED
import com.abhishek101.core.viewmodels.gamelist.ListType.RECENT
import com.abhishek101.core.viewmodels.gamelist.ListType.SHOWCASE
import com.abhishek101.core.viewmodels.gamelist.ListType.TOP_RATED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

enum class ListType(val title: String) {
    SHOWCASE("Showcase"),
    TOP_RATED("Top Rated"),
    COMING_SOON("Coming Soon"),
    RECENT("Recent"),
    MOST_HYPED("Most Hyped"),
    SEARCH_RESULTS("Search Results")
}

class GameListViewModel(
    private val gameRepository: GameRepository,
    private val defaultScope: CoroutineScope
) {
    private val _viewState: MutableStateFlow<GameListViewState> = MutableStateFlow(GameListViewState.Loading)

    val viewState: StateFlow<GameListViewState> = _viewState

    fun getGameLists() {
        _viewState.value = GameListViewState.Loading
        defaultScope.launch {
            combine(
                getListData(SHOWCASE),
                getListData(TOP_RATED),
                getListData(MOST_HYPED),
                getListData(COMING_SOON),
                getListData(RECENT)
            ) { showcaseList, topRatedList, comingSoonList, recentList, mostHypedList ->
                _viewState.value = GameListViewState.NonEmptyViewState(
                    headerList = showcaseList,
                    otherLists = listOf(topRatedList, comingSoonList, recentList, mostHypedList)
                )
            }.collect()
        }
    }

    fun getGameLists(listener: (GameListViewState) -> Unit) {
        defaultScope.launch {
            viewState.onEach {
                listener(it)
            }.collect()
        }
        getGameLists()
    }

    private suspend fun getListData(listType: ListType): Flow<GameListData> {
        return gameRepository.getListDataForType(listType)
    }
}
