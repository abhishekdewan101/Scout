package com.abhishek101.gametracker.ui.features.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.GamePosterRemoteEntity
import com.abhishek101.gametracker.ui.features.home.BottomNavigationState.GAME_LIST
import com.abhishek101.gametracker.ui.features.home.BottomNavigationState.SEARCH
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme
import com.google.accompanist.coil.CoilImage
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get()) {
    HomeScreenContent(
        bottomSelectedTab = viewModel.bottomSelected.value,
        updateBottomSelected = viewModel::updateBottomSelectedState
    )
}

@Composable
fun HomeScreenContent(
    bottomSelectedTab: BottomNavigationState,
    updateBottomSelected: (BottomNavigationState) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            ScaffoldContent(bottomSelectedTab)
        },
        bottomBar = {
            BottomBarContent(
                bottomSelectedTab = bottomSelectedTab,
                updateBottomSelected = updateBottomSelected
            )
        }
    )
}

@Composable
private fun BottomBarContent(
    bottomSelectedTab: BottomNavigationState,
    updateBottomSelected: (BottomNavigationState) -> Unit
) {
    BottomAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        val selectedColor = MaterialTheme.colors.primary
        val unSelectedColor = MaterialTheme.colors.onBackground

        val selectedModifier =
            Modifier
                .weight(1f)
                .padding(horizontal = 35.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.onBackground)

        val unselectedModifier = Modifier.weight(1f)

        IconButton(
            modifier = if (bottomSelectedTab == GAME_LIST) {
                selectedModifier
            } else {
                unselectedModifier
            },
            onClick = { updateBottomSelected(GAME_LIST) }) {
            Icon(
                Icons.Filled.Games,
                "Game Lists",
                tint = if (bottomSelectedTab == GAME_LIST) {
                    selectedColor
                } else {
                    unSelectedColor
                }
            )
        }
        IconButton(
            modifier = if (bottomSelectedTab == SEARCH) {
                selectedModifier
            } else {
                unselectedModifier
            },
            onClick = { updateBottomSelected(SEARCH) }) {
            Icon(
                Icons.Filled.Search, "Search", tint = if (bottomSelectedTab == SEARCH) {
                    selectedColor
                } else {
                    unSelectedColor
                }
            )
        }
    }
}

@Composable
private fun ScaffoldContent(bottomNavigationState: BottomNavigationState) {
    when (bottomNavigationState) {
        SEARCH -> SearchContent()
        GAME_LIST -> ListContent()
    }
}

@Composable
fun ListContent() {
    val viewModel: HomeScreenViewModel = get()
    Column {
        ShowcaseGameList(bannerGameList = viewModel.bannerGameList.value)
        TopRatedGameSection(topRatedGameList = viewModel.topRatedGameList.value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopRatedGameSection(topRatedGameList: List<GamePosterRemoteEntity>) {
    Column {
        Text(
            "Top Rated Games",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            items(topRatedGameList.count()) {
                Column {
                    CoilImage(
                        data = "https://images.igdb.com/igdb/image/upload/t_720p/${topRatedGameList[it].cover.imageId}.jpeg",
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp, 200.dp)
                            .clip(CircleShape.copy(CornerSize(20.dp)))
                            .border(
                                width = 5.dp,
                                color = MaterialTheme.colors.onBackground,
                                shape = MaterialTheme.shapes.large
                            ),
                        error = {
                            Text("Error")
                        },
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                    Text(topRatedGameList[it].name)
                }
            }
        }
    }
}

@Composable
fun ShowcaseGameList(bannerGameList: List<GamePosterRemoteEntity>) {
    LazyRow(Modifier.padding(top = 15.dp)) {
        items(bannerGameList.count()) {
            Column(Modifier.padding(end = 10.dp, start = 10.dp)) {
                CoilImage(
                    data = "https://images.igdb.com/igdb/image/upload/t_720p/${bannerGameList[it].screenShots[0].imageId}.jpeg",
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp, 300.dp)
                        .border(
                            5.dp,
                            color = MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
                Text(
                    bannerGameList[it].name,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun SearchContent() {
    Text(
        "Search Screen",
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground
    )
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL, device = Devices.PIXEL_4_XL)
@Composable
fun HomeScreenContentPreview() {
    GameTrackerTheme {
        HomeScreenContent(SEARCH) { _ -> }
    }
}