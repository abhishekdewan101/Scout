package com.abhishek101.gamescout.features.main.collection

import com.abhishek101.core.models.GameStatus

sealed class CollectionFilter(val title: String, val filter: GameStatus?) {
    object All : CollectionFilter(title = "All", filter = null)
    object WillBuy : CollectionFilter(title = "Will Buy", filter = GameStatus.WANT)
    object NotPlayed : CollectionFilter(title = "Not Played Yet", filter = GameStatus.OWNED)
    object PlayingNow : CollectionFilter(title = "Playing Now", filter = GameStatus.PLAYING)
    object Completed : CollectionFilter(title = "Game Completed", filter = GameStatus.COMPLETED)
    object Abandoned : CollectionFilter(title = "Game Abandoned", filter = GameStatus.ABANDONED)
}
