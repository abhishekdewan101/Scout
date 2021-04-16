package com.abhishek101.gamescout.features.mainapp.home

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
import com.abhishek101.core.utils.buildImageString
import com.abhishek101.gamescout.components.GamePosterGrid
import com.abhishek101.gamescout.components.GamePosterHorizontalList
import com.abhishek101.gamescout.design.HorizontalImageList
import org.koin.androidx.compose.get
import timber.log.Timber

// TODO: Fix the UI code and make the list most modular.
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get()) {
    Column( //fixme: Needs to be lazy column
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
            if (viewModel.showcaseList.value != null) {
                val gameList = viewModel.showcaseList.value
                val imageUrlList = gameList?.games?.map {
                    buildImageString(
                        it.screenShots?.get(0)?.imageId ?: it.cover?.imageId ?: ""
                    )
                }!!
                HorizontalImageList(
                    data = imageUrlList,
                    itemWidth = 400.dp,
                    itemHeight = 200.dp
                ) {
                    Timber.d("User clicked on ${gameList.games[it]}")
                }
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            viewModel.comingSoonList.value?.let { list ->
                val data =
                    list.games.take(9).map { buildImageString(it.cover?.imageId ?: "") }.toList()
                GamePosterGrid(
                    title = list.title,
                    data,
                    3,
                    onViewMoreClicked = {
                        Timber.d("View More List")
                    }
                ) {
                    Timber.d("User clicked on ${list.games[it]}")
                }
            }
        }

        Column(modifier = Modifier.padding(top = 20.dp)) {
            if (viewModel.topRatedList.value != null) {
                val gameList = viewModel.topRatedList.value
                val urls = gameList?.games?.map { buildImageString(it.cover?.imageId ?: "") }!!

                GamePosterHorizontalList(
                    title = "Top Rated",
                    data = urls,
                    onViewMoreClicked = {
                        Timber.d("View More List")
                    }) {
                    Timber.d("User clicked on ${gameList.games[it]}")
                }
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            viewModel.recentList.value?.let { list ->
                val data =
                    list.games.take(9).map { buildImageString(it.cover?.imageId ?: "") }.toList()
                GamePosterGrid(
                    title = list.title,
                    data,
                    3,
                    onViewMoreClicked = {
                        Timber.d("View More List")
                    }
                ) {
                    Timber.d("User clicked on ${list.games[it]}")
                }
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            viewModel.mostHypedList.value?.let { list ->
                val data =
                    list.games.take(9).map { buildImageString(it.cover?.imageId ?: "") }.toList()
                GamePosterGrid(
                    title = list.title,
                    data,
                    3,
                    onViewMoreClicked = {
                        Timber.d("View More List")
                    }
                ) {
                    Timber.d("User clicked on ${list.games[it]}")
                }
            }
        }
    }
}
