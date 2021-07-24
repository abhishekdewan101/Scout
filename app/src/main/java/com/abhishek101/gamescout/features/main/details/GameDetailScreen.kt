package com.abhishek101.gamescout.features.main.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState
import com.abhishek101.gamescout.design.new.image.RemoteImage
import com.abhishek101.gamescout.design.new.system.ProgressIndicator
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

private val COVER_HEIGHT = 250.dp

@ExperimentalMaterialApi
@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = get(),
    data: String,
    updateModalState: (ModalBottomSheetValue) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.viewState.collectAsState()
    val scrollState = rememberScrollState()

    SideEffect {
        viewModel.constructGameDetails(slug = data)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                when (viewState) {
                    GameDetailViewState.EmptyViewState -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                    else -> {
                        GameDetails(game = viewState as GameDetailViewState.NonEmptyViewState, scrollState = scrollState)
                        FadingToolBar(game = viewState as GameDetailViewState.NonEmptyViewState, scrollState = scrollState) {
                            updateModalState(ModalBottomSheetValue.Expanded)
                        }
                    }
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

// region Content
@Composable
private fun GameDetails(game: GameDetailViewState.NonEmptyViewState, scrollState: ScrollState) {
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
                .height(COVER_HEIGHT)
        )
    }
}

// endregion Content

// region TopBar

@Composable
private fun FadingToolBar(game: GameDetailViewState.NonEmptyViewState, scrollState: ScrollState, showAddGameModal: () -> Unit) {
    val alphaOffset = 0f.coerceAtLeast(scrollState.value / COVER_HEIGHT.value).coerceIn(0f..1f)
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground.copy(alpha = alphaOffset),
        elevation = 0.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIos,
                contentDescription = "Back",
                tint = ScoutTheme.colors.topBarTextColor,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable {
                    }
            )

            Text(
                text = game.name,
                style = MaterialTheme.typography.h6,
                color = ScoutTheme.colors.topBarTextColor.copy(alpha = alphaOffset),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(6f)
            )
            Actions(inLibrary = game.inLibrary, showAddGameModal = showAddGameModal)
        }
    }
}

@Composable
private fun RowScope.Actions(inLibrary: Boolean, showAddGameModal: () -> Unit) {
    if (inLibrary) {
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
                    showAddGameModal()
                }
        )
    }
}

// endregion TopBar
