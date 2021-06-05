package com.abhishek101.gamescout.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.ImageGrid
import com.abhishek101.gamescout.design.TitleContainer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GamePosterGrid(
    title: String,
    data: List<String>,
    columns: Int,
    onViewMoreClicked: () -> Unit,
    onItemTapped: (Int) -> Unit
) {
    Column {
        TitleContainer(title = title, hasViewMore = true, onViewMoreClicked = onViewMoreClicked) {
            ImageGrid(
                data = data,
                columns = columns,
                imageWidth = 125.dp,
                imageHeight = 175.dp
            ) {
                onItemTapped(it)
            }
        }
    }
}
