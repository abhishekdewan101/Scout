package com.abhishek101.gametracker.ui.components.titledlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.CoilImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitledGridList(
    title: String,
    data: List<TiledGridListItem>,
    columns: Int,
    onItemTapped: (String) -> Unit
) {
    val items = data.toList().chunked(columns)

    Column {
        Text(title, style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        for (rowItems in items) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            ) {
                for (item in rowItems) {
                    CoilImage(
                        data = item.url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(125.dp, 175.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onItemTapped(item.onTapData)
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
}

data class TiledGridListItem(val onTapData: String, val url: String)
