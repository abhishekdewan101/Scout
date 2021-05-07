package com.abhishek101.gamescout.features.mainapp.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.abhishek101.core.models.GameListData
import com.abhishek101.core.models.IgdbGame
import com.abhishek101.core.models.ListData
import com.abhishek101.gamescout.design.GridImageList
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.SearchTextInput
import com.abhishek101.gamescout.features.mainapp.navigator.LocalMainNavigator
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import org.koin.androidx.compose.get

@Composable
fun SearchScreen(searchScreenViewModel: SearchScreenViewModel = get()) {

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        Column {
            Text(
                "Search",
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            SearchTextInput(
                color = Color(203, 112, 209),
                prefillSearchTerm = searchScreenViewModel.searchTerm.value
            ) {
                searchScreenViewModel.searchForGames(it)
            }

            if (searchScreenViewModel.isSearching.value) {
                LoadingIndicator(color = Color(203, 112, 209))
            }

            if (searchScreenViewModel.searchTerm.value.isNotBlank() &&
                !searchScreenViewModel.isSearching.value
            ) {
                Padding(top = 15.dp) {
                    RenderSearchResults(searchResults = searchScreenViewModel.searchResults.value)
                }
            }
        }

    }
}

@Composable
private fun RenderSearchResults(searchResults: ListData) {
    val results = (searchResults as GameListData).games
    if (results.isEmpty()) {
        RenderNoResults()
    } else {
        RenderSearchGrid(results)
    }
}

@Composable
fun RenderNoResults() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Empty List", color = Color.White)
    }
}

@Composable
private fun RenderSearchGrid(results: List<IgdbGame>) {
    val covers = results.filter { it.cover != null }
        .map { it.cover!!.qualifiedUrl }.toList()
    val mainNavigator = LocalMainNavigator.current
    LazyColumn {
        item {
            GridImageList(
                data = covers,
                columns = 3,
                imageWidth = 125.dp,
                imageHeight = 175.dp
            ) {
                mainNavigator.navigate("${MainAppDestinations.GameDetail.name}/${results[it].slug}")
            }
        }
    }
}
