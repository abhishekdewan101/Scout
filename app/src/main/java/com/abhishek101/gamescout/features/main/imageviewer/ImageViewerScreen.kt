package com.abhishek101.gamescout.features.main.imageviewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState
import com.abhishek101.gamescout.design.image.RemoteImage
import com.abhishek101.gamescout.design.system.ProgressIndicator
import com.abhishek101.gamescout.design.system.SystemUiControlView
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun ImageViewerScreen(
    slug: String,
    viewModel: GameDetailViewModel = get()
) {
    val viewState by viewModel.viewState.collectAsState()

    SideEffect {
        viewModel.constructGameDetails(slug = slug)
    }

    when (viewState) {
        GameDetailViewState.EmptyViewState -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
        else -> {
            val images = (viewState as GameDetailViewState.NonEmptyViewState).mediaList
            SystemUiControlView(
                statusBarColor = ScoutTheme.colors.imageViewerBackground,
                navigationBarColor = ScoutTheme.colors.imageViewerBackground
            ) {
                Scaffold(
                    backgroundColor = ScoutTheme.colors.imageViewerBackground,
                    topBar = {
                        TopBar()
                    },
                    content = {
                        ImageViewer(images)
                    }
                )
            }
        }
    }
}

@Composable
private fun TopBar() {
    TopAppBar(
        backgroundColor = Color.Transparent,
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
        }
    }
}

@Composable
private fun ImageViewer(images: List<String>) {
    BoxWithConstraints {
        LazyRow(modifier = Modifier.fillMaxSize()) {
            items(images) {
                RemoteImage(
                    data = it,
                    contentDescription = "game screenshot",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(maxWidth)
                        .height(maxHeight)
                        .padding(10.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                )
            }
        }
    }
}
