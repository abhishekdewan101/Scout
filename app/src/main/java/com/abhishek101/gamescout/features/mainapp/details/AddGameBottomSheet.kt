package com.abhishek101.gamescout.features.mainapp.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.GameStatus
import com.abhishek101.core.viewmodels.gamedetails.GameAdditionViewState
import com.abhishek101.core.viewmodels.gamedetails.gameStatusMap
import com.abhishek101.core.viewmodels.gamedetails.ratingsMap
import com.abhishek101.gamescout.components.ChipSelectionRow
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SelectableChip
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AddGameBottomSheet(
    additionViewState: GameAdditionViewState,
    updateAdditionViewState: (GameAdditionViewState) -> Unit,
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    saveGame: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(additionViewState.gameNotes)) }
    val focusManager = LocalFocusManager.current

    val keyboardActions = KeyboardActions(
        onDone = {
            focusManager.clearFocus()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        BottomSheetHeaderControl()
        PlatformSelection(additionViewState, updateAdditionViewState)
        GameStatusSelection(additionViewState, updateAdditionViewState)
        if (additionViewState.gameStatus == GameStatus.COMPLETED ||
            additionViewState.gameStatus == GameStatus.ABANDONED
        ) {
            GameRating(additionViewState, updateAdditionViewState)
            GameNotes(textFieldValue = textFieldValue, keyboardActions = keyboardActions) {
                textFieldValue = it
            }
        }

        GameAddtionDoneButton(additionViewState, scope, bottomSheetScaffoldState, saveGame, textFieldValue)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun GameNotes(
    textFieldValue: TextFieldValue,
    keyboardActions: KeyboardActions,
    updateTextFieldValue: (TextFieldValue) -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            "Would you like to add notes?",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Padding(start = 10.dp, end = 10.dp) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            TextField(
                value = textFieldValue,
                placeholder = {
                    Text(
                        "Add notes about the game",
                        style = TextStyle(color = MaterialTheme.colors.background.copy(alpha = 0.5f))
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = keyboardActions,
                onValueChange = { value ->
                    updateTextFieldValue(value)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.background,
                    backgroundColor = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}

@Composable
private fun GameRating(
    additionViewState: GameAdditionViewState,
    updateAdditionViewState: (GameAdditionViewState) -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            "How was the game?",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        for (rating in ratingsMap) {
            val borderColor = if (additionViewState.gameRating == rating.key) {
                MaterialTheme.colors.onBackground
            } else {
                Color.Transparent
            }
            Box(
                modifier = Modifier
                    .border(2.dp, borderColor, RoundedCornerShape(percent = 50))
                    .clickable { updateAdditionViewState(additionViewState.copy(gameRating = rating.key)) }
            ) {
                Text(
                    rating.value,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun GameAddtionDoneButton(
    additionViewState: GameAdditionViewState,
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    saveGame: (String) -> Unit,
    textFieldValue1: TextFieldValue
) {
    if (additionViewState.platformList.filter { it.value }.isNotEmpty()) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth()) {
            Padding(start = 10.dp, end = 10.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colors.primary)
                        .clickable {
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                                saveGame(textFieldValue1.text)
                            }
                        }
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Done, "")
                        Text("Done")
                    }
                }
            }
        }
    }
}

@Composable
private fun GameStatusSelection(
    additionViewState: GameAdditionViewState,
    updateAdditionViewState: (GameAdditionViewState) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            "Please select a game status",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Padding(bottom = 10.dp, start = 10.dp, end = 10.dp) {
        FlowRow(modifier = Modifier.fillMaxWidth(), crossAxisSpacing = 15.dp) {
            for (status in gameStatusMap) {
                SelectableChip(
                    selected = status.value == additionViewState.gameStatus,
                    selectedColor = MaterialTheme.colors.primary,
                    unSelectedColor = MaterialTheme.colors.background,
                    selectedBorderColor = MaterialTheme.colors.onBackground,
                    unSelectedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                    borderWidth = 1.dp,
                    cornerShape = MaterialTheme.shapes.large,
                    data = status.key,
                    selectedTextColor = Color.White,
                    unSelectedTextColor = MaterialTheme.colors.onBackground
                ) {
                    updateAdditionViewState(additionViewState.copy(gameStatus = status.value))
                }
            }
        }
    }
}

@Composable
private fun PlatformSelection(
    additionViewState: GameAdditionViewState,
    updateAdditionViewState: (GameAdditionViewState) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            "Which platform do you own?",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Padding(start = 10.dp, end = 10.dp) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            ChipSelectionRow(chipData = additionViewState.platformList) {
                val newPlatformMap = additionViewState.platformList.toMutableMap()
                newPlatformMap[it] = !newPlatformMap[it]!!
                updateAdditionViewState(additionViewState.copy(platformList = newPlatformMap))
            }
        }
    }
}

@Composable
private fun BottomSheetHeaderControl() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Padding(top = 10.dp) {
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(4.dp)
                    .background(Color.White)
            )
        }
    }
}
