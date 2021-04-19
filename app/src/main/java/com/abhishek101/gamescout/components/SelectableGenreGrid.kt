package com.abhishek101.gamescout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.Genre
import com.abhishek101.gamescout.design.CircularSelectableImage
import com.abhishek101.gamescout.features.genreselection.genreImageMap

@Composable
fun SelectableGenreGrid(data: List<Genre>, columns: Int, onSelected: (String, Boolean) -> Unit) {
    val chunked = data.chunked(columns)
    LazyColumn {
        for (row in chunked) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    for (item in row) {
                        CircularSelectableImage(
                            imageId = genreImageMap[item.slug]!!,
                            label = item.name,
                            isSelected = item.isFavorite!!
                        ) {
                            onSelected(item.slug, item.isFavorite!!.not())
                        }
                    }
                }
            }
        }
    }
}

private fun getItemPadding(index: Int, columns: Int): Modifier {
    return when (index) {
        0 -> Modifier.padding(end = 5.dp)
        columns - 1 -> Modifier.padding(start = 5.dp)
        else -> Modifier.padding(horizontal = 5.dp)
    }
}