package com.abhishek101.gamescout.design

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage

@Composable
fun ImageGrid(
    data: List<String>,
    columns: Int,
    imageWidth: Dp,
    imageHeight: Dp,
    onIndexSelected: (Int) -> Unit
) {
    val chunkedData = data.chunked(columns)

    for (row in chunkedData) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            for ((index, item) in row.withIndex()) {
                CoilImage(
                    data = item,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageWidth, imageHeight)
                        .then(getItemPadding(index = index, columns = columns))
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onIndexSelected(data.indexOf(item))
                        },
                    error = {
                        Text("Error")
                    },
                    loading = {
                        CircularProgressIndicator()
                    }
                )
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
