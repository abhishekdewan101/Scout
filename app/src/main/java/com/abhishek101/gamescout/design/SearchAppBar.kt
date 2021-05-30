package com.abhishek101.gamescout.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

val MIN_HEIGHT = 72.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchAppBar(
    title: String,
    placeholderText: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    executeSearch: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var searchInput by remember { mutableStateOf(TextFieldValue("")) }

    val focusManager = LocalFocusManager.current

    val keyboardActions = KeyboardActions(
        onSearch = {
            focusManager.clearFocus()
            executeSearch(searchInput.text)
        }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(MIN_HEIGHT)
            .background(backgroundColor),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = slideIn({ fullSize -> IntOffset(fullSize.width + 100, 0) }),
            exit = slideOut({ fullSize -> IntOffset(fullSize.width + 100, 0) })
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = searchInput,
                    placeholder = { Text(placeholderText, style = TextStyle(color = MaterialTheme.colors.background.copy(alpha = 0.5f))) },
                    onValueChange = { searchInput = it },
                    modifier = Modifier.weight(9f),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = keyboardActions,
                    singleLine = true,
                    colors = textFieldColors(
                        textColor = MaterialTheme.colors.background,
                        backgroundColor = MaterialTheme.colors.onBackground
                    )
                )
                IconButton(onClick = {
                    expanded = false
                    searchInput = TextFieldValue("")
                }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Outlined.Close, "", tint = MaterialTheme.colors.onBackground)
                }
            }
        }

        AnimatedVisibility(
            visible = !expanded,
            enter = slideIn({ IntOffset(0, 0) }),
            exit = slideOut({ IntOffset(0, 0) })
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title, style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Outlined.Search, "", tint = MaterialTheme.colors.onBackground)
                }
            }
        }
    }
}