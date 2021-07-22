package com.abhishek101.gamescout.features.main.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState
import com.abhishek101.gamescout.design.new.image.RemoteImage
import com.abhishek101.gamescout.design.new.system.ProgressIndicator
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = get(),
    data: String
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.viewState.collectAsState()

    SideEffect {
        viewModel.constructGameDetails(slug = data)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { GameDetailTopBar(viewState = viewState) },
        content = {
            when (viewState) {
                GameDetailViewState.EmptyViewState -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                else -> GameDetails(game = viewState as GameDetailViewState.NonEmptyViewState)
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

// region Content
@Composable
private fun GameDetails(game: GameDetailViewState.NonEmptyViewState) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        HeaderImage(mediaList = game.mediaList)
    }
}

@Composable
private fun HeaderImage(mediaList: List<String>) {
    if (mediaList.isNotEmpty()) {
        RemoteImage(
            request = mediaList.random(),
            contentDescription = "game cover image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}

// endregion Content

// region TopBar
@Composable
private fun GameDetailTopBar(viewState: GameDetailViewState) {
    when {
        viewState is GameDetailViewState.EmptyViewState -> LoadingTopBar()
        viewState is GameDetailViewState.NonEmptyViewState -> RegularTopBar(game = viewState)
    }
}

@Composable
fun LoadingTopBar() {
    TopAppBar(backgroundColor = ScoutTheme.colors.topBarBackground) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.h6,
                color = ScoutTheme.colors.topBarTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RegularTopBar(game: GameDetailViewState.NonEmptyViewState) {
    TopAppBar(backgroundColor = ScoutTheme.colors.topBarBackground) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = ScoutTheme.colors.topBarTextColor,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f)
                    .clickable {
                    }
            )
            Text(
                text = game.name,
                style = MaterialTheme.typography.h6,
                color = ScoutTheme.colors.topBarTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(6f)
            )

            if (game.inLibrary) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = ScoutTheme.colors.topBarTextColor,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .weight(1f)
                        .clickable {
                        }
                )
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = ScoutTheme.colors.topBarTextColor,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .weight(1f)
                        .clickable {
                        }
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = "Save",
                    tint = ScoutTheme.colors.topBarTextColor,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .weight(1f)
                        .clickable {
                        }
                )
            }
        }
    }
}

// endregion TopBar
