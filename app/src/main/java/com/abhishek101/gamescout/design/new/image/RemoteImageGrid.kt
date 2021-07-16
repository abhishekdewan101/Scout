package com.abhishek101.gamescout.design.new.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
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
import com.abhishek101.core.models.IgdbGame

@Composable
fun RemoteImageGrid(
    data: List<IgdbGame>,
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
                    Box(
                        modifier = Modifier
                            .width(preferredWidth)
                            .height(preferredHeight)
                            .padding(end = if (index == row.size - 1) 0.dp else 5.dp)
                            .clickable { onTap(game.slug) }
                    ) {
                        RemoteImage(
                            request = game.cover!!.qualifiedUrl,
                            contentDescription = game.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
        }
    }
}
