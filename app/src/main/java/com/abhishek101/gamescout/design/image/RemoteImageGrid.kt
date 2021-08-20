package com.abhishek101.gamescout.design.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RemoteImageGrid(
    data: List<GridItem>,
    columns: Int,
    preferredWidth: Dp,
    preferredHeight: Dp,
    modifier: Modifier = Modifier,
    onTap: (String) -> Unit
) {
    val rows = data.chunked(columns)
    Column(modifier = modifier) {
        rows.forEachIndexed { index, row ->
            Row(
                horizontalArrangement = SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (index == rows.size - 1) 0.dp else 5.dp)
            ) {
                row.forEachIndexed { index, game ->
                    RemoteImage(
                        data = game.coverUrl,
                        contentDescription = game.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(preferredWidth)
                            .height(preferredHeight)
                            .padding(end = if (index == row.size - 1) 0.dp else 5.dp)
                            .clickable { onTap(game.slug) }
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }
        }
    }
}
