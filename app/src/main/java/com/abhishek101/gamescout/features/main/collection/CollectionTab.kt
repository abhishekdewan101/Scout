package com.abhishek101.gamescout.features.main.collection

import LazyRemoteImageGrid
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Filter
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.viewmodels.library.LibraryViewModel
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.design.image.RemoteImage
import com.abhishek101.gamescout.design.image.toGridItem
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun CollectionTab(libraryViewModel: LibraryViewModel = get(), navigateToScreen: (AppScreens, String) -> Unit) {
    val viewState by libraryViewModel.libraryGames.collectAsState()
    var filter by rememberSaveable { mutableStateOf<CollectionFilter>(CollectionFilter.All) }

    SideEffect {
        libraryViewModel.getLibraryGames()
    }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CollectionTopBar(currentFilter = filter) {
                filter = it
            }
        },
        content = {
            if (viewState.isEmpty()) {
                EmptyLibrary()
            } else {
                Library(games = viewState) {
                    navigateToScreen(AppScreens.DETAIL, it)
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
fun EmptyLibrary() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        RemoteImage(request = R.drawable.corgi, contentDescription = "Empty Corgi", modifier = Modifier.size(64.dp))
        Text(
            text = "Oops! No games found",
            style = MaterialTheme.typography.body1,
            color = ScoutTheme.colors.textOnSecondaryBackground,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
private fun Library(games: List<LibraryGame>, onTap: (String) -> Unit) {
    val data = games.map { it.toGridItem() }
    LazyRemoteImageGrid(
        data = data,
        columns = 3,
        preferredWidth = 130.dp,
        preferredHeight = 180.dp,
        onTap = onTap
    )
}

@Composable
private fun CollectionTopBar(currentFilter: CollectionFilter, updateFilter: (CollectionFilter) -> Unit) {
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            Text(
                text = "Collection",
                fontWeight = FontWeight.Bold,
                color = ScoutTheme.colors.topBarTextColor
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.Filter,
                contentDescription = "filter",
                tint = ScoutTheme.colors.topBarTextColor,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {
                    }
            )
        }
    )
}
