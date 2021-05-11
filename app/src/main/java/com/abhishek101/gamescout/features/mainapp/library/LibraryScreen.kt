package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.gamescout.design.SafeArea
import org.koin.androidx.compose.get

@Composable
fun LibraryScreen() {
    val libraryRepository: LibraryRepository = get()
    val gameList = libraryRepository.getLibraryGames().collectAsState(initial = null)
    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Text(
                    "Library",
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            if (gameList.value != null) {
                items(gameList.value!!.size) {
                    Text(
                        gameList.value!![it].name + gameList.value!![it].status?.name + gameList.value!![it].platform,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            }
        }
    }
}
