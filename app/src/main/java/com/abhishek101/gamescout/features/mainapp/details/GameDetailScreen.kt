package com.abhishek101.gamescout.features.mainapp.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.gamescout.design.LoadingIndicator
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
        LoadingIndicator(Color(203, 112, 209))
    }
}

@Composable
fun RenderGameDetails(gameDetails: IgdbGameDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        //FIXME: This should either show a artwork or screenshot.
        gameDetails.artworks?.let {
            CoilImage(
                data = it[0].qualifiedUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                fadeIn = true,
                contentScale = ContentScale.Crop,
                loading = {
                    LoadingIndicator()
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
}
