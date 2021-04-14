package com.abhishek101.gamescout.components.carousel

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.TouchableImage

@Composable
fun Carousel(
    data: Map<String, String>,
    itemWidth: Dp,
    itemHeight: Dp,
    onItemSelected: (String) -> Unit
) {
    val urlList = data.values.toList()
    val ids = data.keys.toList()
    LazyRow {
        items(urlList.count()) {
            Padding(end = 20.dp) {
                TouchableImage(
                    url = urlList[it],
                    width = itemWidth,
                    height = itemHeight,
                    cornerRadius = 10.dp,
                    backgroundColor = null,
                    rippleColor = MaterialTheme.colors.primary
                ) {
                    onItemSelected(ids[it])
                }
            }
        }
    }
}
