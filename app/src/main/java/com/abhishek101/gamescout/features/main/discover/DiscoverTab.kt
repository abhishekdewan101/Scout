package com.abhishek101.gamescout.features.main.discover

import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamelist.GameListData
import com.abhishek101.core.viewmodels.gamelist.GameListViewModel
import com.abhishek101.core.viewmodels.gamelist.GameListViewState
import com.abhishek101.core.viewmodels.gamelist.GameListViewState.Loading
import com.abhishek101.gamescout.design.image.RemoteImage
import com.abhishek101.gamescout.design.image.RemoteImageGrid
import com.abhishek101.gamescout.design.image.toGridItem
import com.abhishek101.gamescout.design.system.ProgressIndicator
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

const val POSTER_GRID_SIZE = 9

@Composable
fun DiscoverTab(
    viewModel: GameListViewModel = get(),
    navigateToScreen: (AppScreens, String) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    SideEffect {
        if (viewState is Loading) {
            viewModel.getGameLists()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { DiscoverTopBar() },
        content = {
            when (viewState) {
                Loading -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                else -> {
                    val state = viewState as GameListViewState.NonEmptyViewState
                    DiscoverContent(
                        data = state,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        navigateToScreen = navigateToScreen
                    )
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun DiscoverContent(
    data: GameListViewState.NonEmptyViewState,
    navigateToScreen: (AppScreens, String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScoutTheme.colors.secondaryBackground)
    ) {
        item {
            ScreenshotShowcase(data = data.headerList) { slug ->
                navigateToScreen(AppScreens.DETAIL, slug)
            }
        }
        items(data.otherLists.size) {
            val showShowGrid = it % 2 != 0
            if (showShowGrid) {
                CoverGrid(
                    data = data.otherLists[it],
                    onMoreClicked = { type ->
                        navigateToScreen(AppScreens.VIEW_MORE, type)
                    }
                ) { slug ->
                    navigateToScreen(AppScreens.DETAIL, slug)
                }
            } else {
                CoverList(
                    data = data.otherLists[it],
                    onMoreClicked = { type ->
                        navigateToScreen(AppScreens.VIEW_MORE, type)
                    }
                ) { slug ->
                    navigateToScreen(AppScreens.DETAIL, slug)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun CoverList(
    data: GameListData,
    onMoreClicked: (String) -> Unit,
    onTap: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        TitleBar(title = data.title) {
            onMoreClicked(data.listType.name)
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            val games = data.games.filter { it.cover != null }.take(POSTER_GRID_SIZE)
            items(games) {
                RemoteImage(
                    data = it.cover!!.qualifiedUrl,
                    contentDescription = it.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(175.dp)
                        .height(225.dp)
                        .padding(end = 10.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onTap(it.slug) }
                )
            }

            item {
                ViewMoreButton {
                    onMoreClicked(data.listType.name)
                }
            }
        }
    }
}

@Composable
private fun TitleBar(title: String, onTapViewMore: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = ScoutTheme.colors.textOnSecondaryBackground,
            style = MaterialTheme.typography.h5
        )

        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "view more",
            modifier = Modifier
                .clickable { onTapViewMore() },
            tint = ScoutTheme.colors.textOnSecondaryBackground
        )
    }
}

@Composable
private fun ViewMoreButton(onTap: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(100.dp)
            .height(225.dp)
            .clickable {
                onTap()
            }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = "view more",
                modifier = Modifier.size(32.dp),
                tint = ScoutTheme.colors.textOnSecondaryBackground
            )
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "view more",
                modifier = Modifier.size(16.dp),
                tint = ScoutTheme.colors.secondaryBackground
            )
        }
        Text(
            text = "View More",
            style = MaterialTheme.typography.body1,
            color = ScoutTheme.colors.textOnSecondaryBackground,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CoverGrid(
    data: GameListData,
    onMoreClicked: (String) -> Unit,
    onTap: (String) -> Unit
) {
    val games =
        data.games.filter { it.cover != null }.take(POSTER_GRID_SIZE).map { it.toGridItem() }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        TitleBar(title = data.title) {
            onMoreClicked(data.listType.name)
        }
        RemoteImageGrid(
            data = games,
            columns = 3,
            preferredWidth = 125.dp,
            preferredHeight = 175.dp,
            onTap = onTap,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
private fun ScreenshotShowcase(data: GameListData, onTap: (String) -> Unit) {
    val gamesWithScreenshots = data.games
        .filter { it.screenShots != null }

    BoxWithConstraints {
        LazyRow(modifier = Modifier.padding(top = 5.dp)) {
            items(gamesWithScreenshots) {
                RemoteImage(
                    data = it.screenShots!![0].qualifiedUrl,
                    contentDescription = "game screenshot",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(width = maxWidth - 20.dp)
                        .height(height = 200.dp)
                        .padding(end = 10.dp)
                        .padding(top = 10.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .clickable { onTap(it.slug) }
                )
            }
        }
    }
}

@Composable
private fun DiscoverTopBar() {
    val context = LocalContext.current
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            Text(
                text = "Discover",
                fontWeight = FontWeight.Bold,
                color = ScoutTheme.colors.topBarTextColor
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "preferences",
                tint = ScoutTheme.colors.topBarTextColor,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                "Implement Changing Users Selections",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
            )
        }
    )
}
