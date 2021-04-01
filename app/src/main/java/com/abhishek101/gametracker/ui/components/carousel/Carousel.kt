package com.abhishek101.gametracker.ui.components.carousel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage

@Composable
fun Carousel(data: Map<String, String>, onItemSelected: (String) -> Unit) {
    val urlList = data.values.toList()
    val ids = data.keys.toList()
    LazyRow {
        items(urlList.count()) {
            Box(modifier = Modifier.clickable { onItemSelected(ids[it]) }.padding(horizontal = 10.dp)) {
                CoilImage(data = urlList[it], contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(400.dp, 200.dp).clip(RoundedCornerShape(10.dp)))
            }
        }
    }
}