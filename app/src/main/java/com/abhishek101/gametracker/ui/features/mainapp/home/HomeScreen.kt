package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.gametracker.ui.components.carousel.Carousel
import com.abhishek101.gametracker.ui.components.titledlist.TitledGridList
import org.koin.androidx.compose.get
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get()) {
    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, bottom = 56.dp)
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            "Home",
            style = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 28.sp),
            modifier = Modifier.padding(top = 15.dp)
        )

        Box(modifier = Modifier.padding(top = 20.dp)) {
            Carousel(data = viewModel.carouselGameMap, 400.dp, 200.dp) {
                Timber.d("User clicked on $it")
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            TitledGridList(
                title = "Highly Rated Games",
                viewModel.highlyRatedGameList,
                3,
                onViewMoreClicked = {
                    Timber.d("View More List")
                }
            ) {
                Timber.d("User clicked on $it")
            }
        }

        Column(modifier = Modifier.padding(top = 20.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Coming Soon",
                    style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground)
                )
                IconButton(onClick = { Timber.d("User clicked on view more") }) {
                    Icon(Icons.Outlined.MoreVert, "More", tint = MaterialTheme.colors.onBackground)
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Carousel(data = viewModel.comingSoonGames, 150.dp, 250.dp) {
                Timber.d("User Clicked on $it")
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            TitledGridList(
                title = "Top Single Player Games",
                viewModel.singlePlayerList,
                3,
                onViewMoreClicked = {
                    Timber.d("View More List")
                }
            ) {
                Timber.d("User clicked on $it")
            }
        }

    }
}
