package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.ImageGrid
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.SearchAppBar
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel = get(), navigate: (String) -> Unit) {

    val coverUrls = viewModel.libraryGames?.map { it.coverUrl }

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        BoxWithConstraints {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                stickyHeader {
                    SearchAppBar(title = "Library", placeholderText = "Search library") {

                    }
                }
                if (coverUrls != null) {
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        ImageGrid(
                            data = coverUrls,
                            columns = 3,
                            imageWidth = maxWidth / 3,
                            imageHeight = 175.dp
                        ) {
                            navigate(
                                "${MainAppDestinations.GameDetail}/${
                                    viewModel.libraryGames?.get(
                                        it
                                    )?.slug
                                }"
                            )
                        }
                    }
                }
            }
        }
    }
}
