package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.design.SearchAppBar
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel = get(), navigate: (String) -> Unit) {

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            stickyHeader {
                SearchAppBar(title = "Library", placeholderText = "Search library") {
                    
                }
            }
        }
    }
}
