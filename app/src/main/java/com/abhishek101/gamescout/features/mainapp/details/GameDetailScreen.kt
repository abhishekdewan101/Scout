package com.abhishek101.gamescout.features.mainapp.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.mainapp.navigator.LocalMainNavigator
import com.google.accompanist.coil.CoilImage
import org.koin.androidx.compose.get

@Composable
fun GameDetailScreen(viewModel: GameDetailViewModel = get(), gameSlug: String) {
    val gameDetails = viewModel.gameDetails.value

    val mainNavigator = LocalMainNavigator.current

    BackHandler(true) {
        viewModel.gameDetails.value = null
        mainNavigator.popBackStack()
    }
    viewModel.getGameDetails(gameSlug)

    if (gameDetails != null) {
        RenderGameDetails(gameDetails)
    } else {
        LoadingIndicator(Color(203, 112, 209), backgroundColor = Color.Black)
    }
}

@Composable
fun RenderGameDetails(gameDetails: IgdbGameDetail) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        item { RenderHeaderImage(gameDetails) }
        item { RenderMainContent(gameDetails) }
    }
}

@Composable
fun RenderMainContent(gameDetails: IgdbGameDetail) {
    SafeArea(padding = 15.dp, topOverride = 10.dp) {
        Column {
            RenderCoverImage(gameDetails)
        }
    }
}

@Composable
private fun RenderCoverImage(gameDetails: IgdbGameDetail) {
    gameDetails.cover?.let {
        CoilImage(
            data = it.qualifiedUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(125.dp, 175.dp)
                .clip(RoundedCornerShape(10.dp)),
            error = {
                Text("Error")
            },
            loading = {
                CircularProgressIndicator()
            }
        )
    }
}

@Composable
private fun RenderHeaderImage(gameDetails: IgdbGameDetail) {
    val image =
        gameDetails.artworks?.get(0)?.qualifiedUrl ?: gameDetails.screenShots?.get(0)?.qualifiedUrl
    image?.let {
        CoilImage(
            data = it,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            fadeIn = true,
            contentScale = ContentScale.Crop,
            loading = {
                LoadingIndicator(Color(203, 112, 209))
            },
            error = {
                Icon(
                    Icons.Outlined.Error,
                    "error loading image",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        )
    }
}
