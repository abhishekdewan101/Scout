package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.gamescout.components.SelectableChoice
import com.abhishek101.gamescout.design.CollapsableText
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.HorizontalVideoList
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.MediaGallery
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.TitleContainer
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import com.abhishek101.gamescout.utils.buildYoutubeIntent
import com.google.accompanist.coil.CoilImage
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = get(),
    gameSlug: String,
    navigate: (String) -> Unit
) {
    val currentViewState = viewModel.viewState

    val inLibrary = viewModel.inLibrary

    var developer: Map<String, String>? = null
    currentViewState?.involvedCompanies?.find { it.developer }?.company?.let {
        developer = mapOf(it.slug to it.name)
    }

    val excerpt = if (currentViewState?.summary != null && currentViewState.storyline != null) {
        currentViewState.summary + "\n" + currentViewState.storyline
    } else {
        currentViewState?.summary ?: currentViewState?.storyline ?: ""
    }

    val imageList = mutableListOf<String>()
    currentViewState?.screenShots?.let { list -> imageList.addAll(list.map { it.qualifiedUrl }) }
    currentViewState?.artworks?.let { list -> imageList.addAll(list.map { it.qualifiedUrl }) }

    val videoList = mutableListOf<Triple<String, String, String>>()
    currentViewState?.videos?.let { list ->
        videoList.addAll(list.map {
            Triple(
                it.name,
                it.screenShotUrl,
                it.youtubeUrl
            )
        })
    }

    val similarGamesList =
        currentViewState?.similarGames?.filter { it.cover != null }
            ?.map { Pair(it.slug, it.cover!!.qualifiedUrl) }

    val dlcsList = currentViewState?.dlc?.filter { it.cover != null }
        ?.map { Pair(it.slug, it.cover!!.qualifiedUrl) }

    val context = LocalContext.current

    LaunchedEffect(key1 = gameSlug) {
        viewModel.getGameDetails(gameSlug)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (currentViewState == null) {
            LoadingIndicator()
        } else {
            SafeArea(padding = 10.dp) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    stickyHeader {
                        Header(
                            currentViewState.name,
                            inLibrary,
                            { viewModel.toggleGameInLibrary() },
                            navigate = navigate
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RenderCoverImage(image = currentViewState.cover!!.qualifiedUrl)
                            RenderGameInformation(
                                name = currentViewState.name,
                                developer = developer,
                                rating = currentViewState.totalRating,
                                releaseDate = currentViewState.humanReadableFirstReleaseDate
                            )
                        }
                    }
                    item {
                        RenderGameSummary(gameDetails = excerpt)
                    }
                    item {
                        RenderImages(images = imageList)
                    }
                    item {
                        RenderVideos(videos = videoList) {
                            context.startActivity(buildYoutubeIntent(it))
                        }
                    }
                    item {
                        RenderRelatedGames(
                            title = "Similar Games",
                            games = similarGamesList
                        ) {
                            navigate(it)
                        }
                    }

                    item {
                        RenderRelatedGames(
                            title = "Downloadable Content",
                            games = dlcsList
                        ) {
                            navigate(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(
    title: String,
    inLibrary: Boolean,
    saveGame: () -> Unit,
    navigate: (String) -> Unit
) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.weight(9f)
            ) {
                Icon(
                    Icons.Outlined.ArrowBackIos,
                    "",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.clickable { navigate("") })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    title,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                if (inLibrary) Icons.Filled.BookmarkAdded else Icons.Outlined.BookmarkAdd,
                "",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .weight(2f)
                    .clickable {
                        saveGame()
                    }
            )
        }
    }
}

@Composable
private fun RenderImages(images: List<String>) {
    Padding(top = 10.dp) {
        TitleContainer(
            title = "Media",
            titleColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            hasViewMore = false
        ) {
            BoxWithConstraints {
                MediaGallery(images = images, maxWidth = maxWidth)
            }
        }

    }
}

@Composable
private fun RenderCoverImage(image: String) {
    CoilImage(
        data = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(175.dp, 225.dp)
            .clip(RoundedCornerShape(10.dp)),
        error = {
            Text("Error")
        },
        loading = {
            CircularProgressIndicator()
        }
    )
}

@Composable
private fun RenderGameInformation(
    name: String,
    developer: Map<String, String>?,
    rating: Float?,
    releaseDate: String
) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            name,
            color = MaterialTheme.colors.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 3
        )

        developer?.let {
            Text(
                it.getValue(it.keys.first()),
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Text(
            releaseDate,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        rating?.let { RenderGameRating(it.toInt()) }

    }
}

@Composable
private fun RenderGameSummary(gameDetails: String?) {
    gameDetails?.let {
        Padding(top = 10.dp) {
            TitleContainer(
                title = "About",
                titleColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                CollapsableText(data = gameDetails, maxLines = 7, fontSize = 16)
            }
        }
    }
}

@Composable
private fun RenderVideos(
    videos: List<Triple<String, String, String>>?,
    launchVideoDeeplink: (String) -> Unit
) {
    videos?.let {
        Padding(top = 10.dp) {
            TitleContainer(
                title = "Videos",
                titleColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                val titles = videos.map { it.first }.toList()
                val screenshots = videos.map { it.second }.toList()
                val youtubeUrls = videos.map { it.third }.toList()
                BoxWithConstraints {
                    HorizontalVideoList(
                        screenshots = screenshots,
                        titles = titles,
                        itemWidth = maxWidth,
                        itemHeight = 200.dp
                    ) {
                        launchVideoDeeplink(youtubeUrls[it])
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderRelatedGames(
    title: String,
    games: List<Pair<String, String>>?,
    navigate: (String) -> Unit
) {
    games?.let { relatedGames ->
        Padding(top = 10.dp) {
            TitleContainer(
                title = title,
                titleColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                val imageList = relatedGames.map { it.second }.toList()
                val slugs = relatedGames.map { it.first }.toList()
                HorizontalImageList(data = imageList, itemWidth = 150.dp, itemHeight = 200.dp) {
                    navigate("${MainAppDestinations.GameDetail.name}/${slugs[it]}")
                }
            }
        }
    }
}

@Composable
private fun RenderPlatforms(
    gameDetails: IgdbGameDetail,
    isPlatformOwned: (String) -> Boolean,
    updatePlatformAsOwned: (String, String) -> Unit
) {
    gameDetails.platform?.let { platforms ->
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Platforms",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    platforms.forEach {
                        SelectableChoice(
                            isSelected = isPlatformOwned(it.slug),
                            text = it.name,
                            selectionColor = Color(203, 112, 209),
                            backgroundColor = Color.Black
                        ) {
                            updatePlatformAsOwned(gameDetails.slug, it.slug)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderGameRating(rating: Int) {
    val backgroundColor = when {
        rating >= 75 -> Color.Green.copy(alpha = 0.5f)
        rating in 55..74 -> Color.Yellow.copy(alpha = 0.5f)
        else -> Color.Red.copy(alpha = 0.5f)
    }
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(50)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                rating.toString(),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
    Spacer(modifier = Modifier.width(10.dp))
}


