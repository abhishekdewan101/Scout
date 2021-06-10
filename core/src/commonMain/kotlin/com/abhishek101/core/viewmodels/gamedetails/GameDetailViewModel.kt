package com.abhishek101.core.viewmodels.gamedetails

import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState.EmptyViewState
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState.NonEmptyViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository,
    private val defaultScope: CoroutineScope
) {

    private val _viewState = MutableStateFlow<GameDetailViewState>(EmptyViewState)

    val viewState: StateFlow<GameDetailViewState> = _viewState

    private val _formState = MutableStateFlow(defaultFormState)

    val formState: StateFlow<GameIntakeFormState> = _formState

    fun constructGameDetails(slug: String) {
        defaultScope.launch {
            val remoteDetails = gameRepository.getGameDetailForSlug(slug)
            libraryRepository.getGameForSlug(slug).collect {
                _viewState.value = buildGameViewState(remoteDetails = remoteDetails, libraryDetails = it)
                _formState.value = buildFormState(remoteDetails = remoteDetails, libraryDetails = it)
            }
        }
    }

    fun updateFormState(newState: GameIntakeFormState) {
        _formState.value = newState
    }

    fun saveGame() {
        (_viewState.value as? NonEmptyViewState)?.let {
            val inLibrary = it.inLibrary
            val slug = it.slug

            if (inLibrary) {
                libraryRepository.updateGame(
                    gameStatus = getGameStatus(_formState.value),
                    platform = getPlatformList(_formState.value),
                    notes = _formState.value.notes,
                    slug = slug
                )
            } else {
                val name = it.name
                val coverUrl = it.coverUrl
                val releaseDate = it.releaseDate
                libraryRepository.insertGameIntoLibrary(
                    slug = slug,
                    name = name,
                    coverUrl = coverUrl,
                    releaseDate = releaseDate.epoch,
                    gameStatus = getGameStatus(_formState.value),
                    platform = getPlatformList(_formState.value),
                    notes = _formState.value.notes,
                )
            }
        }
    }

    fun removeGame() {
        (_viewState.value as? NonEmptyViewState)?.let {
            libraryRepository.removeGameFromLibrary(it.slug)
        }
    }

    private fun getPlatformList(value: GameIntakeFormState): List<String> {
        return value.platforms.filter { it.owned }.map { it.name }
    }

    private fun getGameStatus(value: GameIntakeFormState): GameStatus {
        var gameStatus: GameStatus
        if (value.saveLocation == SaveLocation.WISHLIST) {
            gameStatus = GameStatus.WISHLIST
        } else {
            gameStatus = when (value.completionStatus) {
                CompletionStatus.QUEUED -> GameStatus.QUEUED
                CompletionStatus.ABANDONED -> GameStatus.ABANDONED
                CompletionStatus.COMPLETED -> GameStatus.COMPLETED
            }
            if (value.completionStatus == CompletionStatus.QUEUED && value.queuedStatus == QueuedStatus.NOW) {
                gameStatus = GameStatus.PLAYING
            }
        }
        return gameStatus
    }

    private fun buildFormState(remoteDetails: IgdbGameDetail, libraryDetails: LibraryGame?): GameIntakeFormState {
        val platforms = buildOwnedPlatformList(remoteDetails, libraryDetails)
        val saveLocation = getSaveLocation(libraryDetails)
        val completionStatus = getCompletionStatus(libraryDetails)
        val queuedStatus = getQueuedStatus(libraryDetails)
        return GameIntakeFormState(
            saveLocation = saveLocation,
            platforms = platforms,
            completionStatus = completionStatus,
            queuedStatus = queuedStatus,
            notes = ""
        )
    }

    private fun getQueuedStatus(libraryDetails: LibraryGame?): QueuedStatus {
        return when {
            libraryDetails == null -> QueuedStatus.NEXT
            libraryDetails.gameStatus == GameStatus.PLAYING -> QueuedStatus.NOW
            else -> QueuedStatus.NEXT
        }
    }

    private fun getCompletionStatus(libraryDetails: LibraryGame?): CompletionStatus {
        return when {
            libraryDetails == null -> CompletionStatus.QUEUED
            libraryDetails.gameStatus == GameStatus.COMPLETED -> CompletionStatus.COMPLETED
            libraryDetails.gameStatus == GameStatus.ABANDONED -> CompletionStatus.ABANDONED
            else -> CompletionStatus.QUEUED
        }
    }

    private fun getSaveLocation(libraryDetails: LibraryGame?): SaveLocation {
        return when {
            libraryDetails == null -> SaveLocation.WISHLIST
            libraryDetails.gameStatus == GameStatus.WISHLIST -> SaveLocation.WISHLIST
            else -> SaveLocation.LIBRARY
        }
    }

    private fun buildGameViewState(remoteDetails: IgdbGameDetail, libraryDetails: LibraryGame?): GameDetailViewState {
        val inLibrary = libraryDetails != null
        val coverUrl = getCoverUrl(remoteDetails)
        val rating = getRatingText(remoteDetails)
        val platforms = buildOwnedPlatformList(remoteDetails, libraryDetails)
        val developer = getDeveloper(remoteDetails)
        val summary = getSummary(remoteDetails)
        val mediaList = buildImageList(remoteDetails)
        val videoList = buildVideoList(remoteDetails)
        val similarGames = buildSimilarGames(remoteDetails)
        val dlcs = buildDlcList(remoteDetails)
        val releaseDate = getReleaseDate(remoteDetails)
        return NonEmptyViewState(
            slug = remoteDetails.slug,
            name = remoteDetails.name,
            coverUrl = coverUrl,
            rating = rating,
            releaseDate = releaseDate,
            platforms = platforms,
            developer = developer,
            summary = summary,
            mediaList = mediaList,
            videoList = videoList,
            similarGames = similarGames,
            dlcs = dlcs,
            inLibrary = inLibrary
        )
    }

    private fun getReleaseDate(remoteDetails: IgdbGameDetail) =
        ReleaseDateViewItem(remoteDetails.firstReleaseDate, remoteDetails.humanReadableFirstReleaseDate)

    private fun getCoverUrl(remoteDetails: IgdbGameDetail): String {
        return remoteDetails.cover!!.qualifiedUrl
    }

    private fun getRatingText(remoteDetails: IgdbGameDetail): Int? {
        return if (remoteDetails.totalRating != null) {
            remoteDetails.totalRating.toInt()
        } else {
            null
        }
    }

    private fun buildDlcList(remoteDetails: IgdbGameDetail): List<GamePosterViewItem> {
        val dlcList = mutableListOf<GamePosterViewItem>()
        remoteDetails.dlc?.let {
            val dlcs = it.map { dlc -> GamePosterViewItem(slug = dlc.slug, url = dlc.cover!!.qualifiedUrl) }
            dlcList.addAll(dlcs)
        }
        return dlcList
    }

    private fun buildSimilarGames(remoteDetails: IgdbGameDetail): List<GamePosterViewItem> {
        val similarGames = mutableListOf<GamePosterViewItem>()
        remoteDetails.similarGames?.let {
            val gameList =
                it.filter { game -> game.cover != null }.map { game -> GamePosterViewItem(slug = game.slug, url = game.cover!!.qualifiedUrl) }
            similarGames.addAll(gameList)
        }
        return similarGames
    }

    private fun buildVideoList(remoteDetails: IgdbGameDetail): List<VideoViewItem> {
        val videoList = mutableListOf<VideoViewItem>()
        remoteDetails.videos?.let {
            val videos = it.map { video -> VideoViewItem(name = video.name, screenshotUrl = video.screenShotUrl, youtubeUrl = video.youtubeUrl) }
            videoList.addAll(videos)
        }
        return videoList
    }

    private fun buildImageList(remoteDetails: IgdbGameDetail): List<String> {
        val imageList = mutableListOf<String>()
        remoteDetails.screenShots?.let {
            val urlList = it.map { image -> image.qualifiedUrl }
            imageList.addAll(urlList)
        }
        remoteDetails.artworks?.let {
            val urlList = it.map { image -> image.qualifiedUrl }
            imageList.addAll(urlList)
        }
        return imageList
    }

    private fun getSummary(remoteDetails: IgdbGameDetail): String? {
        return when {
            remoteDetails.summary != null && remoteDetails.storyline != null -> remoteDetails.summary + "\n" + remoteDetails.storyline
            remoteDetails.summary != null -> remoteDetails.summary
            remoteDetails.storyline != null -> remoteDetails.storyline
            else -> null
        }
    }

    private fun getDeveloper(remoteDetails: IgdbGameDetail): DeveloperViewItem? {
        return remoteDetails.involvedCompanies?.find { it.developer }?.company?.let {
            DeveloperViewItem(name = it.name, slug = it.slug)
        }
    }

    private fun buildOwnedPlatformList(remoteDetails: IgdbGameDetail, libraryDetails: LibraryGame?): List<PlatformViewItem> {
        val ownedPlatforms = mutableMapOf<String, Boolean>()
        remoteDetails.platform?.let {
            it.forEach { platform -> ownedPlatforms[platform.name] = false }
        }
        libraryDetails?.let {
            it.platform.forEach { platform -> ownedPlatforms[platform] = true }
        }
        return ownedPlatforms.map { platform -> PlatformViewItem(name = platform.key, owned = platform.value) }
    }
}