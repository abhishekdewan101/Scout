package com.abhishek101.core.viewmodels.gamedetails

sealed class GameDetailViewState {

    data class NonEmptyViewState(
        val slug: String,
        val name: String,
        val coverUrl: String,
        val rating: Int?,
        val releaseDate: ReleaseDateViewItem,
        val platforms: List<PlatformViewItem>,
        val developer: DeveloperViewItem?,
        val summary: String?,
        val mediaList: List<String>,
        val videoList: List<VideoViewItem>,
        val similarGames: List<GamePosterViewItem>,
        val dlcs: List<GamePosterViewItem>,
        val inLibrary: Boolean,
        val genres: List<String>,
    ) : GameDetailViewState()

    object EmptyViewState : GameDetailViewState()
}

data class ReleaseDateViewItem(val epoch: Long, val dateString: String)

data class GamePosterViewItem(val slug: String, val name: String, val url: String)

data class PlatformViewItem(val name: String, val owned: Boolean)

data class DeveloperViewItem(val slug: String, val name: String)

data class VideoViewItem(val name: String, val screenshotUrl: String, val youtubeUrl: String)
