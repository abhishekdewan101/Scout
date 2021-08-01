package com.abhishek101.gamescout.features.main.collection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.abhishek101.core.models.GameStatus

sealed class CollectionFilter(val title: String, val filter: GameStatus?) {
    object All : CollectionFilter(title = "All", filter = null)
    object WillBuy : CollectionFilter(title = "Will Buy", filter = GameStatus.WANT)
    object NotPlayed : CollectionFilter(title = "Not Played Yet", filter = GameStatus.OWNED)
    object PlayingNow : CollectionFilter(title = "Playing Now", filter = GameStatus.PLAYING)
    object PlayingNext : CollectionFilter(title = "Might Play Next", filter = GameStatus.QUEUED)
    object Completed : CollectionFilter(title = "Game Completed", filter = GameStatus.COMPLETED)
    object Abandoned : CollectionFilter(title = "Game Abandoned", filter = GameStatus.ABANDONED)
}

val AllFilters = listOf(
    CollectionFilter.All,
    CollectionFilter.WillBuy,
    CollectionFilter.NotPlayed,
    CollectionFilter.PlayingNow,
    CollectionFilter.PlayingNext,
    CollectionFilter.Completed,
    CollectionFilter.Abandoned,
)

val CollectionFilterSaver = run {
    Saver<MutableState<CollectionFilter>, GameStatus>(
        save = { it.value.filter },
        restore = {
            when (it) {
                GameStatus.WANT -> mutableStateOf(CollectionFilter.WillBuy)
                GameStatus.OWNED -> mutableStateOf(CollectionFilter.NotPlayed)
                GameStatus.QUEUED -> mutableStateOf(CollectionFilter.PlayingNext)
                GameStatus.PLAYING -> mutableStateOf(CollectionFilter.PlayingNow)
                GameStatus.COMPLETED -> mutableStateOf(CollectionFilter.Completed)
                GameStatus.ABANDONED -> mutableStateOf(CollectionFilter.Abandoned)
                else -> mutableStateOf(CollectionFilter.All)
            }
        }
    )
}
