package com.abhishek101.gamescout.features.mainapp.home

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
import com.abhishek101.core.utils.buildImageString
import com.abhishek101.gamescout.components.TitledGridList
import com.abhishek101.gamescout.design.HorizontalImageList
import org.koin.androidx.compose.get
import timber.log.Timber

// TODO: Fix the UI code and make the list most modular.
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
                TitledGridList(
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.topRatedList.value?.title?.let {
                    Text(
                        it,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                }
                IconButton(onClick = { Timber.d("User clicked on view more") }) {
                    Icon(Icons.Outlined.MoreVert, "More", tint = MaterialTheme.colors.onBackground)
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            if (viewModel.topRatedList.value != null) {
                val gameList = viewModel.topRatedList.value
                val imageUrlList = gameList?.games?.map {
                    buildImageString(
                        it.cover?.imageId ?: ""
                    )
                }!!
                HorizontalImageList(
                    data = imageUrlList,
                    itemWidth = 150.dp,
                    itemHeight = 200.dp
                ) {
                    Timber.d("User clicked on ${gameList.games[it]}")
                }
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            viewModel.recentList.value?.let { list ->
                val data =
                    list.games.take(9).map { buildImageString(it.cover?.imageId ?: "") }.toList()
                TitledGridList(
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
                TitledGridList(
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
