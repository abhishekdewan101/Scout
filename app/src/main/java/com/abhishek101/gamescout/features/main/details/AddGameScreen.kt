package com.abhishek101.gamescout.features.main.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState
import com.abhishek101.gamescout.design.new.image.RemoteImage
import com.abhishek101.gamescout.design.new.system.ProgressIndicator
import com.abhishek101.gamescout.theme.ScoutTheme
import com.google.accompanist.insets.imePadding
import kotlin.math.roundToInt

data class GameListModel(val title: String, val value: GameStatus)

@Composable
fun AddGameScreen(viewModel: GameDetailViewModel, closeBottomSheet: () -> Unit) {
    val viewState by viewModel.viewState.collectAsState()

    if (viewState is GameDetailViewState.NonEmptyViewState) {
        val game = viewState as GameDetailViewState.NonEmptyViewState
        val platformSelections = remember { mutableStateListOf<String>() }
        var gameStatusSelection by remember { mutableStateOf<GameStatus?>(null) }
        var ratingSelection by remember { mutableStateOf(50f) }
        val scrollState = remember { ScrollState(0) }
        var notes by remember { mutableStateOf(TextFieldValue()) }
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxHeight - 20.dp)
                    .background(ScoutTheme.colors.secondaryBackground)
                    .verticalScroll(state = scrollState)
            ) {
                Column(modifier = Modifier.imePadding()) {
                    Header(game = game)
                    SelectPlatform(game = game, isPlatformSelected = { platformSelections.contains(it) }) { platform ->
                        if (platformSelections.contains(platform)) {
                            platformSelections.remove(platform)
                        } else {
                            platformSelections.add(platform)
                        }
                    }
                    GameStatus(isGameStatusSelected = { gameStatusSelection == it }) { gameStatus ->
                        gameStatusSelection = gameStatus
                    }
                    if (gameStatusSelection != null && (gameStatusSelection == GameStatus.COMPLETED || gameStatusSelection == GameStatus.ABANDONED)) {
                        RatingAndNotes(currentRating = ratingSelection, updateRating = { ratingSelection = it }, notes = notes) {
                            notes = it
                        }
                    }
                    if (platformSelections.size > 0 && gameStatusSelection != null) {
                        DoneButton {
                            viewModel.saveGameToLibrary(
                                gameStatus = gameStatusSelection!!,
                                platforms = platformSelections,
                                notes = notes.text,
                                rating = ratingSelection.roundToInt()
                            )
                            closeBottomSheet()
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    } else {
        ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
    }
}

@Composable
private fun DoneButton(addGameToLibrary: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(ScoutTheme.colors.secondaryTextOnSecondaryBackground.copy(alpha = 0.3f))
            .clickable { addGameToLibrary() }
    ) {
        Text(
            text = "Done",
            style = MaterialTheme.typography.h6,
            color = ScoutTheme.colors.textOnSecondaryBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RatingAndNotes(currentRating: Float, updateRating: (Float) -> Unit, notes: TextFieldValue, updateNotes: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = "RATE YOU EXPERIENCE",
            color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(ScoutTheme.colors.secondaryTextOnSecondaryBackground.copy(alpha = 0.3f))
                .padding(horizontal = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "Rating:",
                    color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = currentRating.roundToInt().toString(),
                    color = ScoutTheme.colors.textOnSecondaryBackground,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Slider(
                value = currentRating,
                steps = 0,
                valueRange = 0f..100f,
                onValueChange = {
                    updateRating(it)
                },
                colors = SliderDefaults.colors(
                    thumbColor = ScoutTheme.colors.textOnSecondaryBackground,
                    activeTrackColor = ScoutTheme.colors.textOnSecondaryBackground
                )
            )
            Text(
                text = "Notes",
                color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 10.dp)
            )
            TextField(
                value = notes,
                onValueChange = updateNotes,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = ScoutTheme.colors.textOnSecondaryBackground,
                    cursorColor = ScoutTheme.colors.textOnSecondaryBackground,
                    placeholderColor = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = ScoutTheme.colors.secondaryTextOnSecondaryBackground.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 15.dp)
            )
        }
    }
}

@Composable
private fun GameStatus(isGameStatusSelected: (GameStatus) -> Boolean, toggleGameStatus: (GameStatus) -> Unit) {
    val gameStatusList = listOf(
        GameListModel(title = "I want to buy it", value = GameStatus.WANT),
        GameListModel(title = "I own it", value = GameStatus.OWNED),
        GameListModel(title = "I'd like to play it next", value = GameStatus.QUEUED),
        GameListModel(title = "I'm playing it now", value = GameStatus.PLAYING),
        GameListModel(title = "I've completed it", value = GameStatus.COMPLETED),
        GameListModel(title = "I've abandoned the game", value = GameStatus.ABANDONED)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = "SELECT A GAME STATUS",
            color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(ScoutTheme.colors.secondaryTextOnSecondaryBackground.copy(alpha = 0.3f))
                .padding(horizontal = 10.dp)
        ) {
            gameStatusList.forEachIndexed { index, gameListModel ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { toggleGameStatus(gameListModel.value) }
                            .padding(top = if (index == 0) 10.dp else 0.dp)
                            .padding(bottom = if (index == gameStatusList.size - 1) 10.dp else 0.dp)
                    ) {
                        Icon(
                            imageVector = if (isGameStatusSelected(gameListModel.value)) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                            contentDescription = "More",
                            tint = ScoutTheme.colors.textOnSecondaryBackground,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = gameListModel.title,
                            color = ScoutTheme.colors.textOnSecondaryBackground,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    if (index != gameStatusList.size - 1) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            color = ScoutTheme.colors.dividerColor.copy(alpha = 0.12f), // Get this value from the Divider Composables internal value
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectPlatform(
    game: GameDetailViewState.NonEmptyViewState,
    isPlatformSelected: (String) -> Boolean,
    togglePlatformSelections: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = "SELECT YOUR PREFERRED PLATFORMS",
            color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(ScoutTheme.colors.secondaryTextOnSecondaryBackground.copy(alpha = 0.3f))
                .padding(horizontal = 10.dp)
        ) {
            game.platforms.forEachIndexed { index, platform ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { togglePlatformSelections(platform.name) }
                            .padding(top = if (index == 0) 10.dp else 0.dp)
                            .padding(bottom = if (index == game.platforms.size - 1) 10.dp else 0.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlatformSelected(platform.name)) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                            contentDescription = "More",
                            tint = ScoutTheme.colors.textOnSecondaryBackground,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = platform.name,
                            color = ScoutTheme.colors.textOnSecondaryBackground,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    if (index != game.platforms.size - 1) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            color = ScoutTheme.colors.dividerColor.copy(alpha = 0.12f), // Get this value from the Divider Composables internal value
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(game: GameDetailViewState.NonEmptyViewState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        RemoteImage(
            request = game.coverUrl,
            contentDescription = game.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(125.dp)
                .clip(MaterialTheme.shapes.large)
        )
        Text(
            text = game.name,
            color = ScoutTheme.colors.textOnSecondaryBackground,
            style = MaterialTheme.typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}
