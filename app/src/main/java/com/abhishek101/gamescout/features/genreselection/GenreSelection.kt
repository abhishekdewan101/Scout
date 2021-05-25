package com.abhishek101.gamescout.features.genreselection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.Genre
import com.abhishek101.gamescout.design.CircularSelectableImage
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.theme.GameTrackerTheme
import com.abhishek101.gamescout.theme.White
import org.koin.androidx.compose.get

@Composable
fun GenreSelection(
    viewModel: GenreSelectionViewModel = get(),
    setStatusBarColor: (Color, Boolean) -> Unit,
    onGenreSelectionComplete: () -> Unit
) {
    val isLoading = viewModel.isLoading.value
    val genreList = viewModel.genres.value
    val favoriteCount = viewModel.favoriteGenreCount.value

    val backgroundColor = if (MaterialTheme.colors.isLight) MaterialTheme.colors.primary.copy(alpha = 0.5f) else MaterialTheme.colors.primary
    val useDarkIcon = MaterialTheme.colors.isLight

    SideEffect {
        setStatusBarColor(backgroundColor, useDarkIcon)
    }

    GenreSelectionList(
        isLoading = isLoading,
        genreList = genreList,
        backgroundColor = backgroundColor,
        onGenreSelected = viewModel::updateGenreAsFavorite,
        onGenreSelectionComplete = onGenreSelectionComplete,
        favoriteCount = favoriteCount
    )
}

@Composable
fun GenreSelectionList(
    isLoading: Boolean,
    genreList: List<Genre>,
    backgroundColor: Color,
    onGenreSelected: (String, Boolean) -> Unit,
    onGenreSelectionComplete: () -> Unit,
    favoriteCount: Int
) {
    if (isLoading) {
        LoadingIndicator(color = White, backgroundColor = backgroundColor)
    } else {
        RenderListContent(backgroundColor, genreList, onGenreSelected, favoriteCount, onGenreSelectionComplete)
    }
}

@Composable
private fun RenderListContent(
    backgroundColor: Color,
    genreList: List<Genre>,
    onGenreSelected: (String, Boolean) -> Unit,
    favoriteCount: Int,
    onGenreSelectionComplete: () -> Unit
) {
    Box {
        SafeArea(padding = 15.dp, backgroundColor = backgroundColor) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (favoriteCount > 0) 50.dp else 0.dp)
            ) {
                item {
                    Header()
                }
                val chunked = genreList.chunked(2)
                items(chunked.size) { index ->
                    Padding(top = 15.dp) {
                        BoxWithConstraints {
                            val itemWidth = maxWidth / 2 - 10.dp
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                for (item in chunked[index]) {
                                    CircularSelectableImage(
                                        imageUri = genreImageMap[item.slug]!!,
                                        width = itemWidth,
                                        isSelected = item.isFavorite!!,
                                        selectedBorderColor = White
                                    ) {
                                        onGenreSelected(item.slug, item.isFavorite!!.not())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RenderDoneButton(favoriteCount, onGenreSelectionComplete)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun RenderDoneButton(
    favoriteCount: Int,
    onGenreSelectionComplete: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = favoriteCount > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clickable {
                        onGenreSelectionComplete()
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Outlined.Done,
                        "",
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "Genres",
            style = MaterialTheme.typography.h4,
            color = White,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Select the type of games you like",
            style = MaterialTheme.typography.subtitle1,
            color = White
        )
    }
}

@Preview
@Composable
fun GenreSelectionWithData() {
    GameTrackerTheme {
        GenreSelectionList(
            isLoading = false,
            genreList = genreTestData,
            backgroundColor = MaterialTheme.colors.primary,
            onGenreSelected = { _, _ -> },
            onGenreSelectionComplete = {},
            favoriteCount = 1
        )
    }
}

val genreTestData = listOf(
    Genre(1, "adventure", "Adventure", false),
    Genre(1, "shooter", "Shooter", true),
    Genre(1, "platform", "Platformer", true),
)
