package com.abhishek101.gamescout.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.HorizontalImageList
import com.abhishek101.gamescout.design.TitleContainer

@Composable
fun GamePosterHorizontalList(
    title: String,
    data: List<String>,
    onViewMoreClicked: () -> Unit,
    onItemTapped: (Int) -> Unit
) {
    Column {
        TitleContainer(title = title, hasViewMore = true, onViewMoreClicked = onViewMoreClicked) {
            HorizontalImageList(data = data, itemWidth = 150.dp, itemHeight = 200.dp) {
                onItemTapped(it)
            }
        }
    }
}