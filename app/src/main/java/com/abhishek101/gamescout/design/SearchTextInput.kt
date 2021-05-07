package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchTextInput(
    color: Color,
    prefillSearchTerm: String,
    onTextFieldClearing: () -> Unit,
    executeSearch: (String) -> Unit
) {
    val textState = remember {
        if (prefillSearchTerm.isNotBlank()) {
            mutableStateOf(TextFieldValue(text = prefillSearchTerm))
        } else {
            mutableStateOf(TextFieldValue())
        }
    }

    val focusManager = LocalFocusManager.current

    val keyboardActions = KeyboardActions(onSearch = {
        focusManager.clearFocus()
        executeSearch(textState.value.text)
    })

    BoxWithConstraints {
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier.width(maxWidth),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = color,
                focusedBorderColor = color,
                cursorColor = color,
                textColor = MaterialTheme.colors.onBackground,
                trailingIconColor = color
            ),
            trailingIcon = {
                if (textState.value.text.isEmpty()) {
                    Icon(Icons.Outlined.Search, "")
                } else {
                    Icon(Icons.Outlined.Close, "", modifier = Modifier.clickable {
                        onTextFieldClearing()
                        textState.value = TextFieldValue("")
                    })
                }

            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = keyboardActions,
            placeholder = {
                Text(
                    "Enter search term",
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                )
            }
        )
    }
}

@Preview
@Composable
fun SearchTextInputPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        SearchTextInput(Color(203, 112, 209), prefillSearchTerm = "", {}) {

        }
    }
}