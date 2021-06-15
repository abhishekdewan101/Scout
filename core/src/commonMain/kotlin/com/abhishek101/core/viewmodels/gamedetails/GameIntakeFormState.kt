package com.abhishek101.core.viewmodels.gamedetails

enum class SaveLocation {
    LIBRARY,
    WISHLIST
}

enum class CompletionStatus {
    COMPLETED,
    ABANDONED
}

enum class QueuedStatus {
    NOW,
    NEXT
}

data class GameIntakeFormState(
    val saveLocation: SaveLocation,
    val platforms: List<PlatformViewItem>,
    val completionStatus: CompletionStatus,
    val queuedStatus: QueuedStatus,
    val notes: String
)

val defaultFormState = GameIntakeFormState(
    saveLocation = SaveLocation.WISHLIST,
    platforms = listOf(),
    completionStatus = CompletionStatus.ABANDONED,
    queuedStatus = QueuedStatus.NEXT,
    ""
)
