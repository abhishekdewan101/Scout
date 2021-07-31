package com.abhishek101.core.viewmodels.gamedetails

import com.abhishek101.core.db.Genre
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.GenreRepository
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState.EmptyViewState
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState.NonEmptyViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository,
    private val genreRepository: GenreRepository,
    private val defaultScope: CoroutineScope
) {

    private val _viewState = MutableStateFlow<GameDetailViewState>(EmptyViewState)

    val viewState: StateFlow<GameDetailViewState> = _viewState

    private val _additionViewState = MutableStateFlow(defaultFormState)

    val additionViewState: StateFlow<GameAdditionViewState> = _additionViewState

    private val _libraryViewState: MutableStateFlow<LibraryState?> = MutableStateFlow(null)

    val libraryState: StateFlow<LibraryState?> = _libraryViewState

    fun constructGameDetails(slug: String) {
        getGameDetails(slug = slug)
    }

    fun constructGameDetails(
        slug: String,
        gameViewStateListener: (GameDetailViewState) -> Unit,
        libraryListener: (LibraryState?) -> Unit
    ) {
        viewState.onEach {
            gameViewStateListener(it)
        }.launchIn(defaultScope)

        libraryState.onEach {
            libraryListener(it)
        }.launchIn(defaultScope)

        getGameDetails(slug)
    }

    private fun getGameDetails(slug: String) {
        defaultScope.launch {
            val remoteDetails = gameRepository.getGameDetailForSlug(slug)
            genreRepository.getCachedGenres().onEach { genres ->
                libraryRepository.getGameForSlug(slug).onEach { libraryGame ->
                    _viewState.value = buildGameViewState(
                        remoteDetails = remoteDetails,
                        libraryDetails = libraryGame,
                        genres = genres
                    )
                    if (libraryGame != null) {
                        _libraryViewState.value = LibraryState(
                            platformList = libraryGame.platform,
                            gameStatus = libraryGame.gameStatus,
                            gameRating = libraryGame.rating?.toInt(),
                            gameNotes = libraryGame.notes
                        )
                    } else {
                        _libraryViewState.value = null
                    }
                }.collect()
            }.collect()
        }
    }

    fun updateAdditionViewState(newState: GameAdditionViewState) {
        _additionViewState.value = newState
    }

    fun updateGameInLibrary(gameNotes: String?) {
        (_viewState.value as? NonEmptyViewState)?.let {
            val inLibrary = it.inLibrary
            val slug = it.slug

            if (inLibrary) {
                libraryRepository.updateGame(
                    gameStatus = additionViewState.value.gameStatus,
                    platform = additionViewState.value.platformList.filter { it.value }.map { it.key }.toList(),
                    notes = gameNotes ?: additionViewState.value.gameNotes,
                    rating = additionViewState.value.gameRating.toLong(),
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
                    rating = if (additionViewState.value.gameStatus == GameStatus.COMPLETED ||
                        additionViewState.value.gameStatus == GameStatus.ABANDONED
                    ) {
                        additionViewState.value.gameRating.toLong()
                    } else {
                        null
                    },
                    gameStatus = additionViewState.value.gameStatus,
                    platform = additionViewState.value.platformList.filter { it.value }.map { it.key }.toList(),
                    notes = gameNotes ?: additionViewState.value.gameNotes,
                )
            }
        }
    }

    fun saveGameToLibrary(gameStatus: GameStatus, platforms: List<String>, notes: String, rating: Int) {
        (_viewState.value as? NonEmptyViewState)?.let {
            val inLibrary = it.inLibrary
            if (inLibrary) {
                libraryRepository.updateGame(
                    gameStatus = gameStatus,
                    platform = platforms,
                    notes = notes,
                    rating = rating.toLong(),
                    slug = it.slug
                )
            } else {
                val name = it.name
                val coverUrl = it.coverUrl
                val releaseDate = it.releaseDate
                libraryRepository.insertGameIntoLibrary(
                    slug = it.slug,
                    name = name,
                    coverUrl = coverUrl,
                    releaseDate = releaseDate.epoch,
                    rating = rating.toLong(),
                    gameStatus = gameStatus,
                    platform = platforms,
                    notes = notes,
                )
            }
        }
    }

    fun removeGame() {
        (_viewState.value as? NonEmptyViewState)?.let {
            libraryRepository.removeGameFromLibrary(it.slug)
            _additionViewState.value = defaultFormState
        }
    }

    private fun buildGameViewState(remoteDetails: IgdbGameDetail, libraryDetails: LibraryGame?, genres: List<Genre>): GameDetailViewState {
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
        val filteredGenres = getGenres(remoteDetails, genres)
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
            inLibrary = inLibrary,
            genres = filteredGenres
        )
    }

    private fun getGenres(remoteDetails: IgdbGameDetail, genres: List<Genre>): List<String> {
        return genres.filter { remoteDetails.genres.contains(it.id.toInt()) }.map { it.name }.toList()
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
            val dlcs = it.map { dlc ->
                GamePosterViewItem(
                    slug = dlc.slug,
                    url = dlc.cover!!.qualifiedUrl,
                    name = dlc.name
                )
            }
            dlcList.addAll(dlcs)
        }
        remoteDetails.expansions?.let {
            val expansions = it.map { exp ->
                GamePosterViewItem(
                    slug = exp.slug,
                    url = exp.cover!!.qualifiedUrl,
                    name = exp.name
                )
            }
            dlcList.addAll(expansions)
        }
        return dlcList
    }

    private fun buildSimilarGames(remoteDetails: IgdbGameDetail): List<GamePosterViewItem> {
        val similarGames = mutableListOf<GamePosterViewItem>()
        remoteDetails.similarGames?.let {
            val gameList =
                it.filter { game -> game.cover != null }
                    .map { game ->
                        GamePosterViewItem(
                            slug = game.slug,
                            url = game.cover!!.qualifiedUrl,
                            name = game.name
                        )
                    }
            similarGames.addAll(gameList)
        }
        return similarGames
    }

    private fun buildVideoList(remoteDetails: IgdbGameDetail): List<VideoViewItem> {
        val videoList = mutableListOf<VideoViewItem>()
        remoteDetails.videos?.let {
            val videos = it.map { video ->
                VideoViewItem(
                    name = video.name,
                    screenshotUrl = video.screenShotUrl,
                    youtubeUrl = video.youtubeUrl
                )
            }
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
