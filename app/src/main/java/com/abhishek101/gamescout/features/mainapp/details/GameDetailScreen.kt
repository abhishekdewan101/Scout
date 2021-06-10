package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.abhishek101.core.viewmodels.gamedetails.DeveloperViewItem
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState.EmptyViewState
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState.NonEmptyViewState
import com.abhishek101.core.viewmodels.gamedetails.GameIntakeFormState
import com.abhishek101.core.viewmodels.gamedetails.GamePosterViewItem
import com.abhishek101.core.viewmodels.gamedetails.VideoViewItem
import com.abhishek101.gamescout.components.AddGameForm
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = get(),
    gameSlug: String,
    navigate: (String) -> Unit
) {
    val currentViewState by viewModel.viewState.collectAsState()
    val formState by viewModel.formState.collectAsState()

    LaunchedEffect(key1 = gameSlug) {
        viewModel.constructGameDetails(gameSlug)
    }

    when (currentViewState) {
        EmptyViewState -> LoadingIndicator()
        is NonEmptyViewState -> GameDetailContent(
            viewState = currentViewState as NonEmptyViewState,
            formState = formState,
            updateFormState = viewModel::updateFormState,
            saveGame = viewModel::saveGame,
            removeGame = viewModel::removeGame,
            navigate = navigate
        )
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GameDetailContent(
    viewState: NonEmptyViewState,
    formState: GameIntakeFormState,
    updateFormState: (GameIntakeFormState) -> Unit,
    saveGame: () -> Unit,
    removeGame: () -> Unit,
    navigate: (String) -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                AddGameForm(
                    formState = formState,
                    updateFormData = { updateFormState(it) }
                ) {
                    scope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                        saveGame()
                    }
                }
            }
        ) {
            SafeArea(padding = 10.dp) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    stickyHeader {
                        Header(
                            viewState.name,
                            viewState.inLibrary,
                            removeGame
                        ) {
                            scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RenderCoverImage(image = viewState.coverUrl)
                            RenderGameInformation(
                                name = viewState.name,
                                developer = viewState.developer,
                                rating = viewState.rating,
                                releaseDate = viewState.releaseDate.dateString
                            )
                        }
                    }
                    item {
                        RenderGameSummary(gameDetails = viewState.summary)
                    }
                    item {
                        RenderImages(images = viewState.mediaList)
                    }
                    item {
                        RenderVideos(videos = viewState.videoList) {
                            context.startActivity(buildYoutubeIntent(it))
                        }
                    }
                    item {
                        RenderRelatedGames(
                            title = "Similar Games",
                            games = viewState.similarGames
                        ) {
                            navigate(it)
                        }
                    }

                    item {
                        RenderRelatedGames(
                            title = "Downloadable Content",
                            games = viewState.dlcs
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
    removeGame: () -> Unit,
    saveGame: () -> Unit
) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                modifier = Modifier.weight(9f),
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                if (inLibrary) Icons.Filled.Edit else Icons.Outlined.BookmarkAdd,
                "",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        saveGame()
                    }
            )
            if (inLibrary) {
                Icon(
                    Icons.Filled.Delete,
                    "",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            removeGame()
                        }
                )
            }
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
    developer: DeveloperViewItem?,
    rating: Int?,
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
                it.name,
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

        rating?.let { RenderGameRating(it) }

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
    videos: List<VideoViewItem>,
    launchVideoDeeplink: (String) -> Unit
) {
    if (videos.isNotEmpty()) {
        Padding(top = 10.dp) {
            TitleContainer(
                title = "Videos",
                titleColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                BoxWithConstraints {
                    HorizontalVideoList(
                        videos = videos,
                        itemWidth = maxWidth,
                        itemHeight = 200.dp
                    ) {
                        launchVideoDeeplink(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderRelatedGames(
    title: String,
    games: List<GamePosterViewItem>,
    navigate: (String) -> Unit
) {
    if (games.isNotEmpty()) {
        Padding(top = 10.dp) {
            TitleContainer(
                title = title,
                titleColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                //TODO: Fix HorizontalImagelist to use a GamePosterViewItem
                val imageList = games.map { it.url }.toList()
                val slugs = games.map { it.slug }.toList()
                HorizontalImageList(
                    data = imageList,
                    itemWidth = 150.dp,
                    itemHeight = 200.dp
                ) {
                    navigate("${MainAppDestinations.GameDetail.name}/${slugs[it]}")
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


