package com.abhishek101.gamescout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.Platform
import com.abhishek101.core.utils.buildImageString
import com.abhishek101.gamescout.design.CircularSelectableImage

@Composable
fun SelectablePlatformGrid(
    data: List<Platform>,
    columns: Int,
    onSelected: (String, Boolean) -> Unit
) {
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
                            imageId = buildImageString(item.imageId),
                            label = item.name,
                            isSelected = item.isOwned!!,
                            selectedBorderColor = MaterialTheme.colors.primaryVariant
                        ) {
                            onSelected(item.slug, item.isOwned!!.not())
                        }
                    }
                }
            }
        }
    }
}
