package com.abhishek101.gamescout.features.main.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.search.SearchViewModel
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTab(
    viewModel: SearchViewModel = get()
) {
    val scaffoldState = rememberScaffoldState()
    val recentSearchViewState by viewModel.recentSearchState.collectAsState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SearchTopBar()
        },
        content = {
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun SearchTopBar() {
    var isTopBarEditing by remember { mutableStateOf(false) }
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            if (isTopBarEditing) {
                EditingSearchBar {
                    isTopBarEditing = false
                }
            } else {
                RegularSearchBar {
                    isTopBarEditing = true
                }
            }
        }
    )
}

@Composable
private fun RegularSearchBar(toggleEditingMode: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Search",
            fontWeight = FontWeight.Bold,
            color = ScoutTheme.colors.topBarTextColor
        )
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "search",
            tint = ScoutTheme.colors.topBarTextColor,
            modifier = Modifier
                .padding(end = 5.dp)
                .clickable {
                    toggleEditingMode()
                }
        )
    }
}

@Composable
private fun EditingSearchBar(toggleEditingMode: () -> Unit) {
    var inputTextField by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onSearch = {
            focusManager.clearFocus()
        }
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = "Back",
            tint = ScoutTheme.colors.topBarTextColor,
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f)
                .clickable {
                    toggleEditingMode()
                }
        )
        TextField(
            value = inputTextField,
            placeholder = {
                Text(
                    "Enter game name... ",
                    style = TextStyle(color = ScoutTheme.colors.topBarTextColor)
                )
            },
            modifier = Modifier.weight(8f),
            onValueChange = { inputTextField = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = keyboardActions,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = ScoutTheme.colors.topBarBackground,
                unfocusedIndicatorColor = ScoutTheme.colors.topBarBackground,
                cursorColor = ScoutTheme.colors.topBarTextColor,
                textColor = ScoutTheme.colors.topBarTextColor,
                backgroundColor = ScoutTheme.colors.topBarBackground
            )
        )
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Clear",
            tint = ScoutTheme.colors.topBarTextColor,
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f)
                .clickable {
                    inputTextField = TextFieldValue()
                }
        )
    }
}
