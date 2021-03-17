package com.abhishek101.gametracker.ui.features.genre

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.Genre
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme
import org.koin.androidx.compose.get

@Composable
fun GenreSelection(viewModel: GenreSelectionViewModel = get(), navigateForward: () -> Unit) {
    val isLoading = viewModel.isLoading
    val genreList = viewModel.genres

    GenreSelectionContent(
        isLoading = isLoading.value,
        genreList = genreList.value,
        navigateForward = navigateForward,
        onGenreSelected = viewModel::updateGenreAsFavorite,
        getFavoriteGenreCount = viewModel::getFavoriteGenreCount
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreSelectionContent(
    isLoading: Boolean,
    genreList: List<Genre>,
    navigateForward: () -> Unit,
    onGenreSelected: (Genre, Boolean) -> Unit,
    getFavoriteGenreCount: () -> Int
) {
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp)) {
            Text(
                "Favorite Genres",
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Text(
                "Select the genres that you like the most. We will use these to tailor your results",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Gray
            )
            if (isLoading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .semantics {
                                testTag = "loadingBar"
                            },
                        color = MaterialTheme.colors.primaryVariant
                    )
                }
            } else {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(count = 2),
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    items(genreList.size) { index ->
                        GenreSelectionListItem(genre = genreList[index], onGenreSelected)
                    }
                }
            }
        }

        if (getFavoriteGenreCount() > 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = navigateForward,
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                ) {
                    Text("Done")
                }
            }
        }

    }
}

@Composable
fun GenreSelectionListItem(genre: Genre, onSelected: (Genre, Boolean) -> Unit) {
    val nonHighLighted = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White)

    val highLighted = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .border(2.dp, color = MaterialTheme.colors.secondaryVariant)
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White)

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)
        .clickable {
            onSelected(genre, genre.isFavorite?.not() ?: false)
        }) {
        Box(
            modifier = if (genre.isFavorite == true) {
                highLighted
            } else {
                nonHighLighted
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    genre.name,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4_XL, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GenreSelectionContentLoadingState() {
    GameTrackerTheme {
        GenreSelectionContent(
            isLoading = true,
            genreList = listOf(),
            navigateForward = { /*TODO*/ },
            onGenreSelected = { _, _ -> },
            getFavoriteGenreCount = { return@GenreSelectionContent 0}
        )
    }
}

@Preview(device = Devices.PIXEL_4_XL, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GenreSelectionContentListState() {
    GameTrackerTheme {
        GenreSelectionContent(
            isLoading = false,
            genreList = listOf(Genre(0, "action", "action", isFavorite = false)),
            navigateForward = { /*TODO*/ },
            onGenreSelected = { _, _ -> },
            getFavoriteGenreCount =  { return@GenreSelectionContent 1}
        )
    }
}
