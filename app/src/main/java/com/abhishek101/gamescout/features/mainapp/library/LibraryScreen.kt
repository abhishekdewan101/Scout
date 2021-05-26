package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.LibraryGameStatus
import com.abhishek101.gamescout.design.GridImageList
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.TitleContainer
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@Composable
fun LibraryScreen(viewModel: LibraryViewModel = get(), navigate: (String) -> Unit) {

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Text(
                    "Library",
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                viewModel.deleteAllGames()
                            }
                        )
                    }
                )
            }

            item {
                val libraryGames = viewModel.libraryGames.value
                if (libraryGames != null) {
                    val wishList = libraryGames.filter { it.status == LibraryGameStatus.WISHLISTED }
                    val listData = wishList.map { it.coverUrl }
                    SafeArea(padding = 0.dp, topOverride = 10.dp) {
                        TitleContainer(title = "WishList", hasViewMore = false) {
                            GridImageList(
                                data = listData,
                                columns = 3,
                                imageWidth = 125.dp,
                                imageHeight = 175.dp
                            ) {
                                navigate("${MainAppDestinations.GameDetail.name}/${wishList[it].slug}")
                            }
                        }
                    }
                }
            }

            item {
                val libraryGames = viewModel.libraryGames.value
                if (libraryGames != null) {
                    val listData = libraryGames.map { it.coverUrl }
                    SafeArea(padding = 0.dp, topOverride = 10.dp) {
                        TitleContainer(title = "All Games", hasViewMore = false) {
                            GridImageList(
                                data = listData,
                                columns = 3,
                                imageWidth = 125.dp,
                                imageHeight = 175.dp
                            ) {
                                navigate("${MainAppDestinations.GameDetail.name}/${libraryGames[it].slug}")
                            }
                        }
                    }
                }
            }
        }
    }
}
