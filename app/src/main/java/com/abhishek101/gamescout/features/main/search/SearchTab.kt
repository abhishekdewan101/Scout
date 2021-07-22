package com.abhishek101.gamescout.features.main.search

import LazyRemoteImageGrid
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.IgdbGame
import com.abhishek101.core.viewmodels.search.SearchViewModel
import com.abhishek101.core.viewmodels.search.SearchViewState
import com.abhishek101.gamescout.design.new.system.ProgressIndicator
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTab(
    viewModel: SearchViewModel = get(),
    navigateToScreen: (AppScreens, String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val recentSearchViewState by viewModel.recentSearchState.collectAsState()
    var isTopBarEditing by rememberSaveable { mutableStateOf(false) }
    var inputTextField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SearchTopBar(
                isTopBarEditing = isTopBarEditing,
                inputTextField = inputTextField,
                updateInputTextField = {
                    inputTextField = it
                },
                toggleEditingMode = {
                    isTopBarEditing = it
                    if (!isTopBarEditing) {
                        viewModel.resetSearchState()
                    }
                }
            ) {
                viewModel.searchForGame(inputTextField.text)
            }
        },
        content = {
            if (!isTopBarEditing) {
                if (recentSearchViewState.isNotEmpty()) {
                    RecentSearchList(searchTerms = recentSearchViewState) {
                        isTopBarEditing = true
                        inputTextField = TextFieldValue(it)
                        viewModel.searchForGame(inputTextField.text)
                    }
                }
            } else {
                when (viewState) {
                    is SearchViewState.Loading -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                    is SearchViewState.SearchResults -> {
                        val results = (viewState as SearchViewState.SearchResults).results
                        SearchResults(results = results) {
                            navigateToScreen(AppScreens.DETAIL, it)
                        }
                    }
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

@Composable
private fun SearchResults(results: List<IgdbGame>, onTap: (String) -> Unit) {
    val games = results.filter { it.cover != null }

    LazyRemoteImageGrid(
        data = games,
        columns = 3,
        preferredWidth = 130.dp,
        preferredHeight = 180.dp,
        onTap = onTap,
        modifier = Modifier
            .padding(top = 10.dp)
    )
}

@Composable
private fun RecentSearchList(searchTerms: List<String>, updateInputTextField: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        Text(
            text = "Recent Searches",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = ScoutTheme.colors.textOnSecondaryBackground,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = ScoutTheme.colors.textOnSecondaryBackground,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            searchTerms.forEachIndexed { index, text ->
                Column(modifier = Modifier.clickable { updateInputTextField(text) }) {
                    Text(
                        text = text,
                        color = ScoutTheme.colors.textOnSecondaryBackground,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(vertical = 10.dp)
                    )
                    if (index != searchTerms.size - 1) {
                        Divider(color = ScoutTheme.colors.textOnSecondaryBackground)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTopBar(
    isTopBarEditing: Boolean,
    inputTextField: TextFieldValue,
    updateInputTextField: (TextFieldValue) -> Unit,
    toggleEditingMode: (Boolean) -> Unit,
    searchGame: () -> Unit
) {
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground,
        title = {
            if (isTopBarEditing) {
                EditingSearchBar(inputTextField = inputTextField, updateInputTextField = updateInputTextField, searchGame = searchGame) {
                    toggleEditingMode(false)
                    updateInputTextField(TextFieldValue())
                }
            } else {
                RegularSearchBar {
                    toggleEditingMode(true)
                }
            }
        }
    )
}

@Composable
private fun RegularSearchBar(toggleEditingMode: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
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
                .padding(end = 10.dp)
                .clickable {
                    toggleEditingMode()
                }
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun EditingSearchBar(
    inputTextField: TextFieldValue,
    updateInputTextField: (TextFieldValue) -> Unit,
    searchGame: () -> Unit,
    toggleEditingMode: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onSearch = {
            focusManager.clearFocus()
            searchGame()
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
            onValueChange = updateInputTextField,
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
                    updateInputTextField(TextFieldValue())
                }
        )
    }
}
