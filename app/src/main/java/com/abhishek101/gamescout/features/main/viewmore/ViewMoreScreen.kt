package com.abhishek101.gamescout.features.main.viewmore

import LazyRemoteImageGrid
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamelist.GameListData
import com.abhishek101.core.viewmodels.gamelist.GameListViewModel
import com.abhishek101.core.viewmodels.gamelist.GameListViewState
import com.abhishek101.core.viewmodels.gamelist.GameListViewState.Loading
import com.abhishek101.gamescout.design.image.toGridItem
import com.abhishek101.gamescout.design.system.ProgressIndicator
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun ViewMoreScreen(
    viewModel: GameListViewModel = get(),
    listType: String,
    navigateBack: () -> Unit,
    navigateToScreen: (AppScreens, String) -> Unit,
) {
    val viewState by viewModel.viewMoreViewState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = viewModel) {
        if (viewState is Loading) {
            viewModel.getGameListForType(listType = listType)
        }
    }

    BackHandler {
        navigateBack()
        viewModel.resetViewMoreState()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { ViewMoreTopBar((viewState as? GameListViewState.ViewMoreViewState)?.data) },
        content = {
            when (viewState) {
                Loading -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                else -> {
                    val data = (viewState as GameListViewState.ViewMoreViewState).data
                    ViewMoreGrid(data = data) {
                        navigateToScreen(AppScreens.DETAIL, it)
                    }
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun ViewMoreGrid(data: GameListData, onTap: (String) -> Unit) {
    val games = data.games.filter { it.cover != null }.map { it.toGridItem() }
    LazyRemoteImageGrid(
        data = games,
        columns = 3,
        preferredWidth = 130.dp,
        preferredHeight = 180.dp,
        bottomPadding = 10.dp,
        onTap = onTap
    )
}

@Composable
private fun ViewMoreTopBar(data: GameListData?) {
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = data?.title ?: "Loading...",
                    color = ScoutTheme.colors.topBarTextColor,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}
