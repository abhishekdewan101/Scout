package com.abhishek101.gamescout.design

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalImageList(
    data: List<String>,
    itemWidth: Dp,
    itemHeight: Dp,
    onIndexSelected: (Int) -> Unit
) {
    LazyRow {
        items(data.size) { index ->
            val url = data[index]
            Padding(end = 20.dp) {
                TouchableImage(
                    url = url,
                    width = itemWidth,
                    height = itemHeight,
                    cornerRadius = 10.dp,
                    rippleColor = MaterialTheme.colors.primary
                ) {
                    onIndexSelected(index)
                }
            }
        }
    }
}