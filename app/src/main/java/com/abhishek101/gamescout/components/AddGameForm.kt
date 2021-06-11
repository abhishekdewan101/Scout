package com.abhishek101.gamescout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.gamedetails.CompletionStatus
import com.abhishek101.core.viewmodels.gamedetails.GameIntakeFormState
import com.abhishek101.core.viewmodels.gamedetails.PlatformViewItem
import com.abhishek101.core.viewmodels.gamedetails.QueuedStatus
import com.abhishek101.core.viewmodels.gamedetails.SaveLocation
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.theme.White

// TODO:This class needs to be refactored to a better UX
@Composable
fun AddGameForm(
    formState: GameIntakeFormState,
    updateFormData: (GameIntakeFormState) -> Unit,
    saveGame: () -> Unit,
) {

    val platforms = formState.platforms
    val saveLocation = formState.saveLocation
    val notes = formState.notes
    var comments by remember { mutableStateOf(TextFieldValue(notes)) }
    Padding(all = 10.dp) {
        LazyColumn {
            item {
                Text(
                    "Add game to",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            item {
                Padding(top = 15.dp) {
                    ChipSelectionRow(
                        chipData = mapOf(
                            "WishList" to (saveLocation == SaveLocation.WISHLIST),
                            "Library" to (saveLocation == SaveLocation.LIBRARY)
                        )
                    ) {
                        val updatedSaveLocation = when (it) {
                            "WishList" -> SaveLocation.WISHLIST
                            else -> SaveLocation.LIBRARY
                        }
                        updateFormData(formState.copy(saveLocation = updatedSaveLocation))
                    }
                }
            }

            if (saveLocation == SaveLocation.WISHLIST) {
                item {
                    PlatformSelectionContainer(platforms) { platform ->
                        val newPlatformList = platforms.toMutableList().map {
                            if (it.name == platform) {
                                it.copy(owned = it.owned.not())
                            } else {
                                it
                            }
                        }
                        updateFormData(formState.copy(platforms = newPlatformList))
                    }
                }
            } else {
                item {
                    LibrarySelectionContainer(
                        formState = formState,
                        comments = comments,
                        updateComments = { comments = it }
                    ) {
                        updateFormData(it)
                    }
                }
            }

            item {
                Padding(top = 15.dp) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .background(MaterialTheme.colors.primary)
                            .clickable {
                                saveGame()
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
    }
}

@Composable
fun GameCompletedSelectionContainer(
    comments: TextFieldValue,
    updateComments: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onDone = {
            focusManager.clearFocus()
        }
    )
    Padding(top = 15.dp) {
        Column {
            Text(
                "Rate and comment",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = comments,
                placeholder = {
                    Text(
                        "Add comments about the game",
                        style = TextStyle(color = MaterialTheme.colors.background.copy(alpha = 0.5f))
                    )
                },
                onValueChange = { updateComments(it) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = keyboardActions,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.background,
                    backgroundColor = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}

@Composable
fun QueuedGameSelectionContainer(queuedStatus: QueuedStatus, updateQueueStatus: (QueuedStatus) -> Unit) {
    Padding(top = 15.dp) {
        Column {
            Text(
                "Queued this game for",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            ChipSelectionRow(
                chipData = mapOf(
                    "Now" to (queuedStatus == QueuedStatus.NOW),
                    "Next" to (queuedStatus == QueuedStatus.NEXT),
                )
            ) {
                val newQueuedStatus = when (it) {
                    "Now" -> QueuedStatus.NOW
                    else -> QueuedStatus.NEXT
                }
                updateQueueStatus(newQueuedStatus)
            }
        }
    }
}

@Composable
fun LibrarySelectionContainer(
    formState: GameIntakeFormState,
    comments: TextFieldValue,
    updateComments: (TextFieldValue) -> Unit,
    updateFormData: (GameIntakeFormState) -> Unit
) {
    val platforms = formState.platforms
    val completionStatus = formState.completionStatus
    val queuedStatus = formState.queuedStatus
    Padding(top = 15.dp) {
        Column {
            PlatformSelectionContainer(platforms) { platform ->
                val newPlatformList = platforms.toMutableList().map {
                    if (it.name == platform) {
                        it.copy(owned = it.owned.not())
                    } else {
                        it
                    }
                }
                updateFormData(formState.copy(platforms = newPlatformList))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Game Status",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            ChipSelectionRow(
                chipData = mapOf(
                    "Queue Game" to (completionStatus == CompletionStatus.QUEUED),
                    "Game Completed" to (completionStatus == CompletionStatus.COMPLETED),
                    "Didn't Finish" to (completionStatus == CompletionStatus.ABANDONED)
                )
            ) {
                val newCompletionStatus = when (it) {
                    "Queue Game" -> CompletionStatus.QUEUED
                    "Game Completed" -> CompletionStatus.COMPLETED
                    else -> CompletionStatus.ABANDONED
                }
                updateFormData(formState.copy(completionStatus = newCompletionStatus))
            }
            Spacer(modifier = Modifier.height(10.dp))
            when (completionStatus) {
                CompletionStatus.QUEUED -> QueuedGameSelectionContainer(queuedStatus = queuedStatus) {
                    updateFormData(formState.copy(queuedStatus = it))
                }
                else -> GameCompletedSelectionContainer(comments) {
                    updateComments(it)
                }
            }
        }
    }
}

@Composable
private fun PlatformSelectionContainer(
    selectedPlatforms: List<PlatformViewItem>,
    updatePlatform: (String) -> Unit
) {
    Padding(top = 15.dp) {
        Column {
            Text(
                "Platforms",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            val data = selectedPlatforms.map { it.name to it.owned }.toMap()
            ChipSelectionRow(chipData = data) {
                updatePlatform(it)
            }
        }
    }
}
