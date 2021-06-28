package com.abhishek101.gamescout.features.mainapp.viewmore

import androidx.activity.compose.BackHandler
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamelist.ListType
import com.abhishek101.gamescout.design.HeadingContainer
import com.abhishek101.gamescout.design.LazyGridImageList
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@Composable
fun ViewMoreScreen(
    viewModel: ViewMoreViewModel = get(),
    listType: ListType?,
    navigate: (String) -> Unit
) {
    val data = viewModel.listData.value
    BackHandler(true) {
        viewModel.listData.value = null
        navigate("")
    }
    SafeArea(padding = 15.dp) {
        if (listType == null) {
            Text("Error Cannot Load", style = MaterialTheme.typography.h1)
        } else {
            if (data != null) {
                val games = data.games.filter { it.cover != null }
                val covers = games.map { it.cover!!.qualifiedUrl }.toList()
                HeadingContainer(heading = data.title) {
                    LazyGridImageList(
                        data = covers, columns = 3, imageWidth = 125.dp,
                        imageHeight = 175.dp
                    ) {
                        navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
                    }
                }
            } else {
                viewModel.getGameList(listType)
                LoadingIndicator()
            }
        }
    }
}
