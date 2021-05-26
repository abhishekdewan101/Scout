package com.abhishek101.gamescout.features.mainapp.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.gamescout.components.SelectableChoice
import com.abhishek101.gamescout.design.CollapsableText
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.HorizontalVideoList
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.TitleContainer
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import com.abhishek101.gamescout.utils.buildYoutubeIntent
import com.google.accompanist.coil.CoilImage
import org.koin.androidx.compose.get
import kotlin.math.roundToInt

@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = get(),
    gameSlug: String,
    navigate: (String) -> Unit
) {
    val gameDetails = viewModel.gameDetails.value

    val context = LocalContext.current

    BackHandler(true) {
        viewModel.gameDetails.value = null
        navigate("")
    }

    if (gameDetails == null) {
        viewModel.getGameDetails(gameSlug)
    }

    if (gameDetails != null) {
        RenderGameDetails(
            gameDetails,
            viewModel::isPlatformOwned,
            viewModel::updatePlatformAsOwned,
            { destination ->
                viewModel.onDestroy()
                navigate(destination)
            },
            { videoUrl -> context.startActivity(buildYoutubeIntent(videoUrl)) }
        )
    } else {
        LoadingIndicator(Color(203, 112, 209), backgroundColor = Color.Black)
    }
}

@Composable
fun RenderGameDetails(
    gameDetails: IgdbGameDetail,
    isPlatformOwned: (String) -> Boolean,
    updatePlatformAsOwned: (String, String) -> Unit,
    navigate: (String) -> Unit,
    launchVideoDeeplink: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        item { RenderHeaderImage(gameDetails) }
        item {
            RenderMainContent(
                gameDetails,
                isPlatformOwned,
                updatePlatformAsOwned,
                navigate,
                launchVideoDeeplink
            )
        }
    }
}

@Composable
fun RenderMainContent(
    gameDetails: IgdbGameDetail,
    isPlatformOwned: (String) -> Boolean,
    updatePlatformAsOwned: (String, String) -> Unit,
    navigate: (String) -> Unit,
    launchVideoDeeplink: (String) -> Unit
) {
    SafeArea(padding = 15.dp, topOverride = 10.dp) {
        Column {
            Row {
                RenderCoverImage(gameDetails)
                RenderGameInformation(gameDetails)
            }
            RenderPlatforms(gameDetails, isPlatformOwned, updatePlatformAsOwned)
            RenderGameSummary(gameDetails)
            RenderGameStoryline(gameDetails)
            RenderScreenShots(gameDetails)
            RenderArtwork(gameDetails)
            RenderVideos(gameDetails, launchVideoDeeplink)
            RenderSimilarGames(gameDetails, navigate)
            RenderDlcs(gameDetails, navigate)
            Spacer(modifier = Modifier.height(20.dp))
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
private fun RenderDlcs(gameDetails: IgdbGameDetail, navigate: (String) -> Unit) {
    gameDetails.dlc?.let { similarGames ->
        val dlcsWithCovers =
            similarGames.filter { it.cover != null }
        val imageIdList = dlcsWithCovers.map { it.cover!!.qualifiedUrl }.toList()
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Downloadable Content",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                HorizontalImageList(data = imageIdList, itemWidth = 150.dp, itemHeight = 200.dp) {
                    navigate("${MainAppDestinations.GameDetail.name}/${dlcsWithCovers[it].slug}")
                }
            }
        }
    }
}

@Composable
private fun RenderSimilarGames(gameDetails: IgdbGameDetail, navigate: (String) -> Unit) {
    gameDetails.similarGames?.let { similarGames ->
        val similarGamesWithCovers =
            similarGames.filter { it.cover != null }
        val imageIdList = similarGamesWithCovers.map { it.cover!!.qualifiedUrl }.toList()
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Similar Games",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                HorizontalImageList(data = imageIdList, itemWidth = 150.dp, itemHeight = 200.dp) {
                    navigate("${MainAppDestinations.GameDetail.name}/${similarGamesWithCovers[it].slug}")
                }
            }
        }
    }
}

@Composable
fun RenderVideos(gameDetails: IgdbGameDetail, launchVideoDeeplink: (String) -> Unit) {
    gameDetails.videos?.let { list ->
        val imageIdList = list.map { video -> video.screenShotUrl }.toList()
        val titles = list.map { video -> video.name }.toList()
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Videos",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                BoxWithConstraints {
                    HorizontalVideoList(
                        screenshots = imageIdList,
                        titles = titles,
                        itemWidth = maxWidth,
                        itemHeight = 200.dp
                    ) {
                        launchVideoDeeplink(list[it].youtubeUrl)
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderArtwork(gameDetails: IgdbGameDetail) {
    gameDetails.artworks?.map { it.qualifiedUrl }?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Artwork",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                BoxWithConstraints {
                    HorizontalImageList(data = it, itemWidth = maxWidth, itemHeight = 200.dp) {}
                }
            }
        }
    }
}

@Composable
private fun RenderScreenShots(gameDetails: IgdbGameDetail) {
    gameDetails.screenShots?.map { it.qualifiedUrl }?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Screenshots",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                BoxWithConstraints {
                    HorizontalImageList(data = it, itemWidth = maxWidth, itemHeight = 200.dp) {}
                }
            }
        }
    }
}

@Composable
private fun RenderGameStoryline(gameDetails: IgdbGameDetail) {
    gameDetails.storyline?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Storyline",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                CollapsableText(data = it, maxLines = 7, fontSize = 16)
            }
        }
    }
}

@Composable
private fun RenderGameSummary(gameDetails: IgdbGameDetail) {
    gameDetails.summary?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Summary",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                CollapsableText(data = it, maxLines = 7, fontSize = 16)
            }
        }
    }
}

@Composable
private fun RenderGameInformation(gameDetails: IgdbGameDetail) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Text(
            gameDetails.name,
            color = Color.White,
            fontSize = 24.sp,
            maxLines = 3
        )
        gameDetails.involvedCompanies?.find { it.developer }?.let {
            Text(
                it.company.name,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RenderGameRating(gameDetails)
                Text(
                    gameDetails.humanReadableFirstReleaseDate,
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
private fun RenderGameRating(gameDetails: IgdbGameDetail) {
    gameDetails.totalRating?.roundToInt()?.let {
        val backgroundColor = when {
            it >= 75 -> Color.Green.copy(alpha = 0.5f)
            it in 55..74 -> Color.Yellow.copy(alpha = 0.5f)
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
                    it.toString(),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
private fun RenderCoverImage(gameDetails: IgdbGameDetail) {
    gameDetails.cover?.let {
        CoilImage(
            data = it.qualifiedUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(125.dp, 175.dp)
                .clip(RoundedCornerShape(10.dp)),
            error = {
                Text("Error")
            },
            loading = {
                CircularProgressIndicator()
            }
        )
    }
}

@Composable
private fun RenderHeaderImage(gameDetails: IgdbGameDetail) {
    val image =
        gameDetails.artworks?.get(0)?.qualifiedUrl ?: gameDetails.screenShots?.get(0)?.qualifiedUrl
    image?.let {
        CoilImage(
            data = it,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            fadeIn = true,
            contentScale = ContentScale.Crop,
            loading = {
                LoadingIndicator(Color(203, 112, 209))
            },
            error = {
                Icon(
                    Icons.Outlined.Error,
                    "error loading image",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        )
    }
}
