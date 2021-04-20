package com.abhishek101.gamescout.features.genreselection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.abhishek101.core.db.Genre
import com.abhishek101.gamescout.components.SelectableGenreGrid
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.onboarding.LocalOnBoardingNavigator
import com.abhishek101.gamescout.features.onboarding.LocalUpdateOnBoardingCompleted
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations
import com.abhishek101.gamescout.features.onboarding.UpdateOnBoardingComplete
import com.abhishek101.gamescout.theme.GameTrackerTheme
import org.koin.androidx.compose.get

@Composable
fun GenreSelection(viewModel: GenreSelectionViewModel = get()) {
    val isLoading = viewModel.isLoading.value
    val genreList = viewModel.genres.value
    val navController = LocalOnBoardingNavigator.current
    val onBoardingCompleted = LocalUpdateOnBoardingCompleted.current

    GenreSelectionList(
        isLoading = isLoading,
        genreList = genreList,
        onGenreSelected = viewModel::updateGenreAsFavorite,
        navController = navController,
        onBoardingCompleted = onBoardingCompleted,
        favoriteCount = viewModel.getFavoriteGenreCount()
    )
}

@Composable
fun GenreSelectionList(
    isLoading: Boolean,
    genreList: List<Genre>,
    onGenreSelected: (String, Boolean) -> Unit,
    navController: NavController,
    onBoardingCompleted: UpdateOnBoardingComplete,
    favoriteCount: Int
) {
    SafeArea(padding = 15.dp, backgroundColor = Color(15, 27, 27)) {
        Box() {
            Column(modifier = Modifier.fillMaxSize()) {
                GenreSelectionHeader()
                if (isLoading) {
                    LoadingIndicator(Color(240, 115, 101))
                } else {
                    Padding(top = 15.dp) {
                        SelectableGenreGrid(
                            data = genreList,
                            columns = 2,
                            onSelected = onGenreSelected
                        )
                    }
                }
            }
            RenderDoneButton(favoriteCount, onBoardingCompleted, navController)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun RenderDoneButton(
    favoriteCount: Int,
    onBoardingCompleted: UpdateOnBoardingComplete,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = favoriteCount > 0) {
            Padding(bottom = 15.dp) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(240, 115, 101))
                    .clickable {
                        onBoardingCompleted()
                        navController.navigate(OnBoardingDestinations.MainAppScreen.toString())
                    }) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Outlined.Done,
                            "",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GenreSelectionHeader() {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            "Genres",
            style = MaterialTheme.typography.h4,
            color = Color(240, 115, 101),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Select your favorite genres and we will use them to tailor your search results",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground
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
            onGenreSelected = { _, _ -> },
            navController = LocalOnBoardingNavigator.current,
            onBoardingCompleted = LocalUpdateOnBoardingCompleted.current,
            favoriteCount = 1
        )
    }
}

val genreTestData = listOf(
    Genre(1, "adventure", "Adventure", false),
    Genre(1, "shooter", "Shooter", true),
    Genre(1, "platform", "Platformer", true),
)
