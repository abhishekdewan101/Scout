package com.abhishek101.gametracker.ui.features.genreselection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.abhishek101.core.db.Genre
import com.abhishek101.gametracker.ui.components.ListItem
import com.abhishek101.gametracker.ui.components.navigation.LocalMainNavController
import com.abhishek101.gametracker.ui.components.navigation.LocalUpdateOnBoardingCompleted
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme
import org.koin.androidx.compose.get

@Composable
fun GenreSelection(viewModel: GenreSelectionViewModel = get()) {
    val isLoading = viewModel.isLoading
    val genreList = viewModel.genres
    val navController = LocalMainNavController.current
    val onBoardingCompleted = LocalUpdateOnBoardingCompleted.current

    GenreSelectionScaffold(
        isLoading = isLoading.value,
        genreList = genreList.value,
        navigateForward = {
            onBoardingCompleted()
            navController.popBackStack()
            navController.navigate(MainNavigatorDestinations.MainAppScreen.name)
        },
        onGenreSelected = viewModel::updateGenreAsFavorite,
        getFavoriteGenreCount = viewModel::getFavoriteGenreCount
    )
}

@Composable
fun GenreSelectionScaffold(
    isLoading: Boolean,
    genreList: List<Genre>,
    onGenreSelected: (Genre, Boolean) -> Unit,
    getFavoriteGenreCount: () -> Int,
    navigateForward: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { GenreSelectionHeader() },
        content = {
            GenreSelectionContent(
                isLoading = isLoading,
                genreList = genreList,
                onGenreSelected = onGenreSelected
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            GenreSelectionFab(
                favoriteGenreCount = getFavoriteGenreCount(),
                navigateForward = navigateForward
            )
        }
    )
}

@Composable
fun GenreSelectionContent(
    isLoading: Boolean,
    genreList: List<Genre>,
    onGenreSelected: (Genre, Boolean) -> Unit
) {
    if (isLoading) {
        GenreSelectionLoading()
    } else {
        GenreSelectionListContent(genreList, onGenreSelected)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreSelectionListContent(genreList: List<Genre>, onGenreSelected: (Genre, Boolean) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count = 2),
        modifier = Modifier.padding(top = 20.dp)
    ) {
        items(genreList.size) { index ->
            genreList[index].apply {
                ListItem(isSelected = this.isFavorite ?: false, data = this.name) {
                    onGenreSelected(this, this.isFavorite?.not() ?: false)
                }
            }
        }
    }
}

@Composable
fun GenreSelectionLoading() {
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
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun GenreSelectionFab(favoriteGenreCount: Int, navigateForward: () -> Unit) {
    if (favoriteGenreCount > 0) {
        FloatingActionButton(
            onClick = navigateForward,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(Icons.Outlined.Done, "Done", tint = MaterialTheme.colors.onPrimary)

        }
    }
}

@Composable
fun GenreSelectionHeader() {
    Box(modifier = Modifier.padding(top = 15.dp, start = 15.dp)) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                "Owned Platforms",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                "We will use these platforms to tailor your search results",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview
@Composable
fun GenreSelectionWithData() {
    GameTrackerTheme {
        GenreSelectionScaffold(
            isLoading = false,
            genreList = genreTestData,
            onGenreSelected = { _, _ -> },
            getFavoriteGenreCount = { return@GenreSelectionScaffold 3 }) {

        }
    }
}

val genreTestData = listOf(
    Genre(1, "slug", "Genre 1", false),
    Genre(1, "slug2", "Genre 2", true),
    Genre(1, "slug3", "Genre 3", true),
)
