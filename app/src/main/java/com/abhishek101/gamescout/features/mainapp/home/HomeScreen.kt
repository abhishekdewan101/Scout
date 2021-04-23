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
import androidx.navigation.compose.navigate
import com.abhishek101.core.models.EmptyList
import com.abhishek101.core.models.GameListData
import com.abhishek101.core.models.ListData
import com.abhishek101.core.repositories.ListType
import com.abhishek101.gamescout.components.GamePosterGrid
import com.abhishek101.gamescout.components.GamePosterHorizontalList
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.mainapp.navigator.LocalMainNavigator
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

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
private fun RenderMostHypedList(mostHypedList: MutableState<ListData>) {
    if (mostHypedList.value != EmptyList) {
        val listData = (mostHypedList.value as GameListData)
        CoverList(listData = listData, listType = ListType.MOST_HYPED)
    }
}

@Composable
private fun RenderRecentList(recentList: MutableState<ListData>) {
    if (recentList.value != EmptyList) {
        val listData = (recentList.value as GameListData)
        CoverList(listData = listData, isGrid = false, listType = ListType.RECENT)
    }
}

@Composable
private fun RenderTopRatedList(topRatedList: MutableState<ListData>) {
    if (topRatedList.value != EmptyList) {
        val listData = (topRatedList.value as GameListData)
        CoverList(listData = listData, isGrid = false, listType = ListType.TOP_RATED)
    }
}

@Composable
private fun RenderComingSoonList(comingSoonList: MutableState<ListData>) {
    if (comingSoonList.value != EmptyList) {
        val listData = (comingSoonList.value as GameListData)
        CoverList(listData = listData, listType = ListType.COMING_SOON)
    }
}

@Composable
private fun RenderShowcaseList(showcaseList: MutableState<ListData>) {
    if (showcaseList.value != EmptyList) {
        val mainNavigator = LocalMainNavigator.current
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
            mainNavigator.navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
        }
    }
}

@Composable
private fun CoverList(listData: GameListData, isGrid: Boolean = true, listType: ListType) {
    val games = listData.games
    val covers = games.filter { it.cover != null }
        .take(9)
        .map { it.cover!!.qualifiedUrl }.toList()
    val mainNavigator = LocalMainNavigator.current
    if (isGrid) {
        GamePosterGrid(
            title = listData.title,
            data = covers,
            columns = 3,
            onViewMoreClicked = { mainNavigator.navigate("${MainAppDestinations.ViewMore.name}/${listType.name}") }) {
            mainNavigator.navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
        }
    } else {
        GamePosterHorizontalList(
            title = listData.title,
            data = covers,
            onViewMoreClicked = { mainNavigator.navigate("${MainAppDestinations.ViewMore.name}/${listType.name}") }) {
            mainNavigator.navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
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
