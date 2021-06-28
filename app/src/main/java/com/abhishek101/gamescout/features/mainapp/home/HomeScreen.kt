package com.abhishek101.gamescout.features.mainapp.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamelist.GameListData
import com.abhishek101.core.viewmodels.gamelist.GameListViewModel
import com.abhishek101.core.viewmodels.gamelist.GameListViewState
import com.abhishek101.core.viewmodels.gamelist.ListType
import com.abhishek101.gamescout.components.GamePosterGrid
import com.abhishek101.gamescout.components.GamePosterHorizontalList
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.SearchAppBar
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: GameListViewModel = get(), navigate: (String) -> Unit) {

    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getGameLists()
    }

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        when (viewState.value) {
            GameListViewState.Loading -> LoadingIndicator()
            else -> {
                val nonEmptyViewState = viewState.value as GameListViewState.NonEmptyViewState
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    stickyHeader {
                        SearchAppBar(title = "Home", placeholderText = "Search games") {
                            navigate("${MainAppDestinations.Search}/$it")
                        }
                    }
                    item {
                        Padding(top = 15.dp) {
                            RenderShowcaseList(nonEmptyViewState.headerList, navigate)
                        }
                    }
                    items(nonEmptyViewState.otherLists.size) {
                        Padding(top = 15.dp) {
                            CoverList(
                                listData = nonEmptyViewState.otherLists[it],
                                isGrid = (it % 2 == 0),
                                listType = nonEmptyViewState.otherLists[it].listType,
                                navigate = navigate
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderShowcaseList(showcaseList: GameListData, navigate: (String) -> Unit) {
    val games = showcaseList.games
    val screenshots = games.filter { it.screenShots != null }
        .take(9)
        .map { it.screenShots!![0].qualifiedUrl }
        .toList()
    BoxWithConstraints {
        HorizontalImageList(
            data = screenshots,
            itemWidth = maxWidth,
            itemHeight = 200.dp
        ) {
            navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
        }
    }
}

@Composable
private fun CoverList(
    listData: GameListData,
    isGrid: Boolean = true,
    listType: ListType,
    navigate: (String) -> Unit
) {
    val games = listData.games.filter { it.cover != null }
    val covers = games
        .take(9)
        .map { it.cover!!.qualifiedUrl }.toList()
    if (isGrid) {
        GamePosterGrid(
            title = listData.title,
            data = covers,
            columns = 3,
            onViewMoreClicked = { navigate("${MainAppDestinations.ViewMore.name}/${listType.name}") }
        ) {
            navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
        }
    } else {
        GamePosterHorizontalList(
            title = listData.title,
            data = covers,
            onViewMoreClicked = { navigate("${MainAppDestinations.ViewMore.name}/${listType.name}") }
        ) {
            navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}
