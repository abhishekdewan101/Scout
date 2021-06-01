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
import com.google.accompanist.coil.CoilImage

@Composable
fun ImageViewer(images: List<String>, maxWidth: Dp) {
    val chunkedData = images.chunked(6)
    LazyRow(modifier = Modifier.height(450.dp)) {
        for (row in chunkedData) {
            val firstGroup = row.take(4)
            item {
                Column {
                    ImageGrid(
                        data = firstGroup,
                        columns = 2,
                        imageWidth = maxWidth / 2,
                        imageHeight = 215.dp
                    ) {
                    }
                }

            }
            item {
                Spacer(modifier = Modifier.width(10.dp))
            }
            if (row.size > 4) {
                val secondGroup = row.subList(4, row.size)
                if (secondGroup.size == 1) {
                    item {
                        CoilImage(
                            data = secondGroup[0],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(maxWidth, 450.dp)
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
                } else {
                    item {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            CoilImage(
                                data = secondGroup[0],
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(maxWidth, 215.dp)
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
                                data = secondGroup[1],
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(maxWidth, 215.dp)
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
                }
                item {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}