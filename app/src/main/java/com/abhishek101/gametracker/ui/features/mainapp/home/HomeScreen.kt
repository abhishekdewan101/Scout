package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
            Carousel(data = viewModel.carouselGameMap.toMap()) {
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
    }
}
