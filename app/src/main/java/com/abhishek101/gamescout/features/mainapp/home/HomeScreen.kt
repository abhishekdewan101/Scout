package com.abhishek101.gamescout.features.mainapp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.EmptyList
import com.abhishek101.core.models.GameListData
import com.abhishek101.core.models.ListData
import com.abhishek101.gamescout.components.GamePosterGrid
import com.abhishek101.gamescout.components.GamePosterHorizontalList
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import org.koin.androidx.compose.get
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get()) {

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        if (viewModel.isAnyDataPresent()) {
            LoadingIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        "Home",
                        style = MaterialTheme.typography.h4.copy(
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                item {
                    Padding(top = 15.dp) {
                        RenderShowcaseList(viewModel.showcaseList)
                    }
                }
                item {
                    Padding(top = 15.dp) {
                        RenderComingSoonList(viewModel.comingSoonList)
                    }
                }
                item {
                    Padding(top = 15.dp) {
                        RenderRecentList(viewModel.recentList)
                    }
                }
                item {
                    Padding(top = 15.dp) {
                        RenderMostHypedList(viewModel.mostHypedList)
                    }
                }
                item {
                    Padding(top = 15.dp, bottom = 15.dp) {
                        RenderTopRatedList(viewModel.topRatedList)
                    }
                }
            }
        }
    }
}

@Composable
fun RenderMostHypedList(mostHypedList: MutableState<ListData>) {
    if (mostHypedList.value != EmptyList) {
        val listData = (mostHypedList.value as GameListData)
        CoverList(listData = listData)
    }
}

@Composable
fun RenderRecentList(recentList: MutableState<ListData>) {
    if (recentList.value != EmptyList) {
        val listData = (recentList.value as GameListData)
        CoverList(listData = listData, isGrid = false)
    }
}

@Composable
fun RenderTopRatedList(topRatedList: MutableState<ListData>) {
    if (topRatedList.value != EmptyList) {
        val listData = (topRatedList.value as GameListData)
        CoverList(listData = listData, isGrid = false)
    }
}

@Composable
fun RenderComingSoonList(comingSoonList: MutableState<ListData>) {
    if (comingSoonList.value != EmptyList) {
        val listData = (comingSoonList.value as GameListData)
        CoverList(listData = listData)
    }
}

@Composable
fun RenderShowcaseList(showcaseList: MutableState<ListData>) {
    if (showcaseList.value != EmptyList) {
        val games = (showcaseList.value as GameListData).games
        val screenshots = games.filter { it.screenShots != null }
            .take(9)
            .map { it.screenShots!![0].qualifiedUrl }
            .toList()
        HorizontalImageList(
            data = screenshots,
            itemWidth = 400.dp,
            itemHeight = 200.dp
        ) {
            Timber.d("User clicked on ${games[it]}")
        }
    }
}

@Composable
private fun CoverList(listData: GameListData, isGrid: Boolean = true) {
    val games = listData.games
    val covers = games.filter { it.cover != null }
        .take(9)
        .map { it.cover!!.qualifiedUrl }.toList()
    if (isGrid) {
        GamePosterGrid(
            title = listData.title,
            data = covers,
            columns = 3,
            onViewMoreClicked = { Timber.d("Show more coming soon games") }) {
            Timber.d("User clicked on ${games[it]}")
        }
    } else {
        GamePosterHorizontalList(
            title = listData.title,
            data = covers,
            onViewMoreClicked = { Timber.d("Show more top rated games") }) {
            Timber.d("User clicked on ${games[it]}")
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
