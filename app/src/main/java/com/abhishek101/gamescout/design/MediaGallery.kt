package com.abhishek101.gamescout.design

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun FourImageGrid(images: List<String>, imageWidth: Dp, imageHeight: Dp) {
    Column {
        ImageGrid(data = images, columns = 2, imageWidth = imageWidth, imageHeight = imageHeight) {}
    }
}

@Composable
internal fun SingleImage(image: String, imageWidth: Dp, imageHeight: Dp) {
    CoilImage(
        data = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(imageWidth, imageHeight)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
            },
        error = {
            Text("Error")
        },
        loading = {
            CircularProgressIndicator()
        }
    )
}

@Composable
internal fun TwoImageColumn(images: List<String>, imageWidth: Dp, imageHeight: Dp) {
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        CoilImage(
            data = images[0],
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageWidth, imageHeight)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                },
            error = {
                Text("Error")
            },
            loading = {
                CircularProgressIndicator()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        CoilImage(
            data = images[1],
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageWidth, imageHeight)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
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

@Composable
fun MediaGallery(images: List<String>, maxWidth: Dp) {
    val chunkedData = images.chunked(6)
    LazyRow(modifier = Modifier.height(450.dp)) {
        for (row in chunkedData) {
            val firstGroup = row.take(4)
            item {
                Column {
                    FourImageGrid(
                        images = firstGroup,
                        imageWidth = maxWidth / 2,
                        imageHeight = 215.dp
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.width(10.dp))
            }

            if (row.size > 4) {
                val secondGroup = row.subList(4, row.size)
                when (secondGroup.size) {
                    1 -> item {
                        SingleImage(
                            image = secondGroup[0],
                            imageWidth = maxWidth,
                            imageHeight = 450.dp
                        )
                    }
                    2 -> item {
                        TwoImageColumn(
                            images = secondGroup,
                            imageWidth = maxWidth,
                            imageHeight = 215.dp
                        )
                    }
                }
                if (secondGroup.isNotEmpty()) {
                    item { Spacer(modifier = Modifier.width(10.dp)) }
                }
            }
        }
    }
}
