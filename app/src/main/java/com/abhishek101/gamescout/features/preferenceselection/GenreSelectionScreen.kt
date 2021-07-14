package com.abhishek101.gamescout.features.preferenceselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewModel
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.design.new.image.SelectableRemoteImage
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

val genreImageMap = mapOf(
    "point-and-click" to R.drawable.point_click,
    "fighting" to R.drawable.fighting,
    "shooter" to R.drawable.shooter,
    "music" to R.drawable.music,
    "platform" to R.drawable.platformer,
    "puzzle" to R.drawable.puzzle,
    "racing" to R.drawable.racing,
    "role-playing-rpg" to R.drawable.role_playing_game,
    "real-time-strategy-rts" to R.drawable.rts,
    "moba" to R.drawable.moba,
    "card-and-board-game" to R.drawable.card_board_games,
    "visual-novel" to R.drawable.visual_novel,
    "arcade" to R.drawable.ic_arcade,
    "indie" to R.drawable.indie_games,
    "adventure" to R.drawable.ic_adventure,
    "pinball" to R.drawable.pinball,
    "quiz-trivia" to R.drawable.quiz_trivia,
    "hack-and-slash-beat-em-up" to R.drawable.hack_slash,
    "tactical" to R.drawable.tactical,
    "turn-based-strategy-tbs" to R.drawable.turn_based,
    "strategy" to R.drawable.stratergy,
    "sport" to R.drawable.sports,
    "simulator" to R.drawable.simulator
)

@Composable
fun GenreSelectionScreen(
    viewModel: PreferenceSelectionViewModel = get(),
    genreSelectionDone: () -> Unit
) {

    val viewState by viewModel.viewState.collectAsState()

    SideEffect {
        viewModel.getGenres()
    }

    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.primaryBackground,
        navigationBarColor = ScoutTheme.colors.primaryBackground,
        useDarkIcons = false
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(ScoutTheme.colors.primaryBackground)
            ) {
                item {
                    Text(
                        text = "Genres",
                        color = ScoutTheme.colors.textOnPrimaryBackground,
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                item {
                    Text(
                        text = "What genres do you like to play?",
                        color = ScoutTheme.colors.textOnPrimaryBackground,
                        style = MaterialTheme.typography.body1
                    )
                }

                item {
                    when (viewState) {
                        PreferenceSelectionViewState.Loading -> LoadingView()
                        else -> GenreListView(
                            result = viewState as PreferenceSelectionViewState.Result
                        ) { slug: String, isFavorite: Boolean ->
                            viewModel.toggleGenre(
                                genreSlug = slug,
                                isFavorite = isFavorite
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(75.dp))
                }
            }

            (viewState as? PreferenceSelectionViewState.Result)?.let {
                if (it.ownedGenreCount > 0) {
                    DoneButtonView(modifier = Modifier.padding(bottom = 15.dp)) {
                        viewModel.setOnBoardingCompleted()
                        genreSelectionDone()
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = ScoutTheme.colors.progressIndicatorOnPrimaryBackground)
    }
}

@Composable
fun GenreListView(result: PreferenceSelectionViewState.Result, toggleGenreSelection: (String, Boolean) -> Unit) {
    val rows = result.genres.chunked(2)
    for (row in rows) {
        BoxWithConstraints(modifier = Modifier.padding(top = 10.dp)) {
            val halfWidth = maxWidth / 2 - 20.dp
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (genre in row) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.sizeIn(maxWidth = halfWidth)
                    ) {
                        SelectableRemoteImage(
                            request = genreImageMap[genre.slug]!!,
                            shape = CircleShape,
                            scaleImageFactor = 0.5f,
                            selectionColor = ScoutTheme.colors.textOnPrimaryBackground,
                            isSelected = genre.isFavorite!!,
                            imageSize = halfWidth,
                            backgroundColor = ScoutTheme.colors.textOnPrimaryBackground
                        ) {
                            toggleGenreSelection(
                                genre.slug,
                                genre.isFavorite!!.not()
                            )
                        }

                        Text(
                            text = genre.name,
                            color = ScoutTheme.colors.textOnPrimaryBackground,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DoneButtonView(
    modifier: Modifier = Modifier,
    onTap: () -> Unit
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = ScoutTheme.colors.secondaryButton,
        contentColor = ScoutTheme.colors.textOnPrimaryBackground
    )

    BoxWithConstraints {
        Button(
            onClick = onTap,
            colors = colors,
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
                .width(maxWidth)
                .height(50.dp)
                .padding(horizontal = 50.dp)
        ) {
            Row {
                Text(
                    text = "Done",
                    color = ScoutTheme.colors.textOnPrimaryBackground,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
