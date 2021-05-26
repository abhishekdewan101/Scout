package com.abhishek101.gamescout.features.mainapp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.abhishek101.core.repositories.ListType
import com.abhishek101.gamescout.components.GamePosterGrid
import com.abhishek101.gamescout.components.GamePosterHorizontalList
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get(), navigate: (String) -> Unit) {

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
                        RenderShowcaseList(viewModel.showcaseList, navigate)
                    }
                }
                item {
                    Padding(top = 15.dp) {
                        RenderComingSoonList(viewModel.comingSoonList, navigate)
                    }
                }
                item {
                    Padding(top = 15.dp) {
                        RenderRecentList(viewModel.recentList, navigate)
                    }
                }
                item {
                    Padding(top = 15.dp) {
                        RenderMostHypedList(viewModel.mostHypedList, navigate)
                    }
                }
                item {
                    Padding(top = 15.dp, bottom = 15.dp) {
                        RenderTopRatedList(viewModel.topRatedList, navigate)
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderMostHypedList(mostHypedList: MutableState<ListData>, navigate: (String) -> Unit) {
    if (mostHypedList.value != EmptyList) {
        val listData = (mostHypedList.value as GameListData)
        CoverList(listData = listData, listType = ListType.MOST_HYPED, navigate = navigate)
    }
}

@Composable
private fun RenderRecentList(recentList: MutableState<ListData>, navigate: (String) -> Unit) {
    if (recentList.value != EmptyList) {
        val listData = (recentList.value as GameListData)
        CoverList(listData = listData, isGrid = false, listType = ListType.RECENT, navigate)
    }
}

@Composable
private fun RenderTopRatedList(topRatedList: MutableState<ListData>, navigate: (String) -> Unit) {
    if (topRatedList.value != EmptyList) {
        val listData = (topRatedList.value as GameListData)
        CoverList(listData = listData, isGrid = false, listType = ListType.TOP_RATED, navigate)
    }
}

@Composable
private fun RenderComingSoonList(
    comingSoonList: MutableState<ListData>,
    navigate: (String) -> Unit
) {
    if (comingSoonList.value != EmptyList) {
        val listData = (comingSoonList.value as GameListData)
        CoverList(listData = listData, listType = ListType.COMING_SOON, navigate = navigate)
    }
}

@Composable
private fun RenderShowcaseList(showcaseList: MutableState<ListData>, navigate: (String) -> Unit) {
    if (showcaseList.value != EmptyList) {
        val games = (showcaseList.value as GameListData).games
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
