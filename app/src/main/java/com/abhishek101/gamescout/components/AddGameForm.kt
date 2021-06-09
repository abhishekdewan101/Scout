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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.viewmodels.gamedetails.PlatformViewItem
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.theme.White

data class AddGameFormData(
    val status: GameStatus,
    val platforms: List<String>,
    val comments: String?,
)

@Composable
fun AddGameForm(
    platforms: List<PlatformViewItem>,
    initialSaveLocation: String,
    initialOwnedStatus: String,
    initialQueuedStatus: String,
    saveFormData: (AddGameFormData) -> Unit
) {
    var saveLocation by remember {
        mutableStateOf(initialSaveLocation)
    }
    var selectedPlatforms = remember {
        mutableStateMapOf<String, Boolean>().also {
            platforms.forEach { platformViewItem -> it[platformViewItem.name] = platformViewItem.owned }
        }
    }

    var ownedStatus by remember {
        mutableStateOf(initialOwnedStatus)
    }

    var comments by remember { mutableStateOf(TextFieldValue("")) }

    var queuedStatus by remember {
        mutableStateOf(initialQueuedStatus)
    }

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
                            "WishList" to (saveLocation == "WishList"),
                            "Library" to (saveLocation == "Library")
                        )
                    ) { saveLocation = it }
                }
            }

            if (saveLocation == "WishList") {
                item {
                    WishlistSelectionContainer(selectedPlatforms) {
                        selectedPlatforms[it] = selectedPlatforms[it]!!.not()
                    }
                }
            } else {
                item {
                    LibrarySelectionContainer(
                        selectedPlatforms,
                        ownedStatus,
                        queuedStatus,
                        comments,
                        { queuedStatus = it },
                        { comments = it },
                        { selectedPlatforms[it] = selectedPlatforms[it]!!.not() })
                    {
                        ownedStatus = it
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
                                saveFormData(
                                    calculateFormData(
                                        saveLocation,
                                        ownedStatus,
                                        selectedPlatforms,
                                        comments,
                                        queuedStatus
                                    )
                                )
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

fun calculateFormData(
    saveLocation: String,
    ownedStatus: String,
    selectedPlatforms: SnapshotStateMap<String, Boolean>,
    comments: TextFieldValue,
    queuedStatus: String
): AddGameFormData {
    val gameStatus = when (saveLocation) {
        "WishList" -> GameStatus.WISHLIST
        else -> when {
            ownedStatus == "Game Completed" -> GameStatus.COMPLETED
            ownedStatus == "Didn't Finish" -> GameStatus.ABANDONED
            queuedStatus == "Now" -> GameStatus.PLAYING
            ownedStatus == "Queue Game" -> GameStatus.QUEUED
            else -> GameStatus.QUEUED
        }
    }
    val platforms = selectedPlatforms.filter { it.value }.keys.toList()
    return AddGameFormData(gameStatus, platforms, comments.text)
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
fun QueuedGameSelectionContainer(queuedStatus: String, updateQueueStatus: (String) -> Unit) {
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
                    "Now" to (queuedStatus == "Now"),
                    "Next" to (queuedStatus == "Next"),
                    "Later" to (queuedStatus == "Later"),
                )
            ) {
                updateQueueStatus(it)
            }
        }
    }
}

@Composable
fun LibrarySelectionContainer(
    platforms: Map<String, Boolean>,
    ownedStatus: String,
    queuedStatus: String,
    comments: TextFieldValue,
    updateQueueStatus: (String) -> Unit,
    updateComments: (TextFieldValue) -> Unit,
    updatePlatform: (String) -> Unit,
    updateOwnedStatus: (String) -> Unit
) {
    Padding(top = 15.dp) {
        Column {
            PlatformSelectionContainer(platforms) {
                updatePlatform(it)
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
                    "Queue Game" to (ownedStatus == "Queue Game"),
                    "Game Completed" to (ownedStatus == "Game Completed"),
                    "Didn't Finish" to (ownedStatus == "Didn't Finish"),
                )
            ) {
                updateOwnedStatus(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            when (ownedStatus) {
                "Queue Game" -> QueuedGameSelectionContainer(queuedStatus) {
                    updateQueueStatus(it)
                }
                else -> GameCompletedSelectionContainer(comments) {
                    updateComments(it)
                }
            }
        }
    }
}

@Composable
fun WishlistSelectionContainer(
    selectedPlatforms: Map<String, Boolean>,
    updatePlatform: (String) -> Unit
) {
    PlatformSelectionContainer(selectedPlatforms, updatePlatform)
}

@Composable
private fun PlatformSelectionContainer(
    selectedPlatforms: Map<String, Boolean>,
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
            ChipSelectionRow(chipData = selectedPlatforms) {
                updatePlatform(it)
            }
        }
    }
}