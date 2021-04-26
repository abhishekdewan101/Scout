package com.abhishek101.gamescout.features.mainapp.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.gamescout.design.CollapsableText
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.TitleContainer
import com.abhishek101.gamescout.features.mainapp.navigator.LocalMainNavigator
import com.google.accompanist.coil.CoilImage
import org.koin.androidx.compose.get
import kotlin.math.roundToInt

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
            Row {
                RenderCoverImage(gameDetails)
                RenderGameInformation(gameDetails)
            }
            RenderGameSummary(gameDetails)
            RenderGameStoryline(gameDetails)
            RenderScreenShots(gameDetails)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun RenderScreenShots(gameDetails: IgdbGameDetail) {
    gameDetails.screenShots?.map { it.qualifiedUrl }?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Screenshots",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                HorizontalImageList(data = it, itemWidth = 400.dp, itemHeight = 200.dp) {}
            }
        }
    }
}

@Composable
private fun RenderGameStoryline(gameDetails: IgdbGameDetail) {
    gameDetails.storyline?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Storyline",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                CollapsableText(data = it, maxLines = 7, fontSize = 16)
            }
        }
    }
}

@Composable
private fun RenderGameSummary(gameDetails: IgdbGameDetail) {
    gameDetails.summary?.let {
        SafeArea(padding = 0.dp, topOverride = 10.dp) {
            TitleContainer(
                title = "Summary",
                titleColor = Color.White.copy(alpha = 0.5f),
                hasViewMore = false
            ) {
                CollapsableText(data = it, maxLines = 7, fontSize = 16)
            }
        }
    }
}

@Composable
private fun RenderGameInformation(gameDetails: IgdbGameDetail) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Text(
            gameDetails.name,
            color = Color.White,
            fontSize = 24.sp,
            maxLines = 3
        )
        gameDetails.involvedCompanies?.find { it.developer }?.let {
            Text(
                it.company.name,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        RenderGameRating(gameDetails)
    }
}

@Composable
private fun RenderGameRating(gameDetails: IgdbGameDetail) {
    SafeArea(padding = 0.dp, topOverride = 10.dp) {
        gameDetails.totalRating?.roundToInt()?.let {
            val backgroundColor = when {
                it >= 75 -> Color.Green.copy(alpha = 0.5f)
                it in 55..74 -> Color.Yellow.copy(alpha = 0.5f)
                else -> Color.Red.copy(alpha = 0.5f)
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(50))
                    .background(backgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(50)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        it.toString(),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
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
