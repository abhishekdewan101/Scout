package com.abhishek101.gamescout.features.mainapp.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.abhishek101.core.models.IgdbGame
import com.abhishek101.core.viewmodels.search.SearchViewModel
import com.abhishek101.core.viewmodels.search.SearchViewState
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.design.CoilImage
import com.abhishek101.gamescout.design.ImageGrid
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.SearchTextInput
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = get(),
    searchTerm: String,
    navigate: (String) -> Unit
) {

    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.searchForGame(searchTerm = searchTerm)
    }

    when (viewState.value) {
        SearchViewState.Loading -> SafeArea(padding = 15.dp) {
            LoadingIndicator()
        }
        else -> {
            val searchResults = viewState.value as SearchViewState.SearchResults
            SafeArea(padding = 15.dp) {
                RenderSearchResults(
                    searchResults = searchResults.results,
                    searchTerm = searchTerm,
                    navigate = navigate
                )
            }
        }
    }
}

@Composable
private fun RenderSearchResults(
    searchResults: List<IgdbGame>,
    searchTerm: String,
    navigate: (String) -> Unit
) {
    if (searchResults.isEmpty()) {
        RenderNoResults()
    } else {
        RenderSearchGrid(searchResults, searchTerm, navigate)
    }
}

@Composable
fun RenderNoResults() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = R.drawable.corgi,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp, 100.dp)
                .padding(bottom = 10.dp),
            error = {
                Text("Error")
            },
            loading = {
                CircularProgressIndicator()
            }
        )
        Text(
            "Oops.. Couldn't find any results",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RenderSearchGrid(
    results: List<IgdbGame>,
    searchTerm: String,
    navigate: (String) -> Unit,
) {
    val games = results.filter { it.cover != null }
    val covers = games.map { it.cover!!.qualifiedUrl }.toList()
    LazyColumn {
        stickyHeader {
            Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
                Text(
                    "Search",
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                SearchTextInput(
                    color = MaterialTheme.colors.onBackground,
                    prefillSearchTerm = searchTerm,
                    onTextFieldClearing = { }
                ) {
                    navigate("${MainAppDestinations.Search.name}/$it")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        item {
            ImageGrid(
                data = covers,
                columns = 3,
                imageWidth = 125.dp,
                imageHeight = 175.dp
            ) {
                navigate("${MainAppDestinations.GameDetail.name}/${games[it].slug}")
            }
        }
    }
}
