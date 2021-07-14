package com.abhishek101.gamescout.features.main.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamelist.GameListData
import com.abhishek101.core.viewmodels.gamelist.GameListViewModel
import com.abhishek101.core.viewmodels.gamelist.GameListViewState
import com.abhishek101.core.viewmodels.gamelist.GameListViewState.Loading
import com.abhishek101.gamescout.design.new.image.RemoteImage
import com.abhishek101.gamescout.design.new.system.ProgressIndicator
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun DiscoverTab(
    viewModel: GameListViewModel = get(),
    navigateToScreen: (AppScreens, String) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    SideEffect {
        viewModel.getGameLists()
    }

    Scaffold(
        topBar = { DiscoverTopBar() },
        content = {
            when (viewState) {
                Loading -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                else -> {
                    val state = viewState as GameListViewState.NonEmptyViewState
                    DiscoverContent(data = state, navigateToScreen = navigateToScreen)
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun DiscoverContent(
    data: GameListViewState.NonEmptyViewState,
    navigateToScreen: (AppScreens, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ScoutTheme.colors.secondaryBackground)
    ) {
        item {
            ScreenshotShowcase(data = data.headerList) {
                navigateToScreen(AppScreens.DETAIL, it)
            }
        }
    }
}

@Composable
private fun ScreenshotShowcase(data: GameListData, onTap: (String) -> Unit) {
    val gamesWithScreenshots = data.games
        .filter { it.screenShots != null }

    BoxWithConstraints {
        LazyRow {
            items(gamesWithScreenshots) {
                RemoteImage(
                    request = it.screenShots!![0].qualifiedUrl,
                    contentDescription = "game screenshot",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(width = maxWidth - 20.dp)
                        .height(height = 200.dp)
                        .padding(horizontal = 10.dp)
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
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            Text(
                text = "Discover",
                fontWeight = FontWeight.Bold,
                color = ScoutTheme.colors.topBarTextColor
            )
        }
    )
}
