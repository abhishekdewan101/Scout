package com.abhishek101.core.viewmodels.gamedetails

import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.GameStatus.ABANDONED
import com.abhishek101.core.models.GameStatus.COMPLETED
import com.abhishek101.core.models.GameStatus.OWNED
import com.abhishek101.core.models.GameStatus.PLAYING
import com.abhishek101.core.models.GameStatus.QUEUED
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

@Suppress("MagicNumber")
val ratingsMap = mapOf(
    1 to "😩",
    2 to "😒",
    3 to "😐",
    4 to "😃",
    5 to "😍",
)

val gameStatusMap = mapOf(
    "Want Game" to WANT,
    "Queue Game" to QUEUED,
    "Owned" to OWNED,
    "Playing Now" to PLAYING,
    "Completed" to COMPLETED,
    "Abandoned" to ABANDONED
)

val defaultFormState = GameAdditionViewState(
    platformList = emptyMap(),
    gameStatus = WANT,
    gameRating = 3,
    gameNotes = ""
)
