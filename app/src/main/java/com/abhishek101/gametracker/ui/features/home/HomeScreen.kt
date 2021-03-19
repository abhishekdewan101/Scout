package com.abhishek101.gametracker.ui.features.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
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
import com.abhishek101.gametracker.ui.features.home.BottomNavigationState.GAME_LIST
import com.abhishek101.gametracker.ui.features.home.BottomNavigationState.SEARCH
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme
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
    Text(
        "List Screen",
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground
    )
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