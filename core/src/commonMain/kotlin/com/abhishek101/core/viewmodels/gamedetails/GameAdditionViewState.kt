package com.abhishek101.core.viewmodels.gamedetails

import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.GameStatus.WANT

data class GameAdditionViewState(
    val platformList: Map<String, Boolean>,
    val gameStatus: GameStatus,
    val gameRating: Int,
    val gameNotes: String
)

data class LibraryState(
    val platformList: List<String>,
    val gameStatus: GameStatus,
    val gameRating: Int?,
    val gameNotes: String?
)

val defaultFormState = GameAdditionViewState(
    platformList = emptyMap(),
    gameStatus = WANT,
    gameRating = 3,
    gameNotes = ""
)
