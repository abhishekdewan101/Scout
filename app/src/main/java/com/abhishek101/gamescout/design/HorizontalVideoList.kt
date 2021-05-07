package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalVideoList(
    screenshots: List<String>,
    titles: List<String>,
    itemWidth: Dp,
    itemHeight: Dp,
    onIndexSelected: (Int) -> Unit
) {
    LazyRow {
        items(screenshots.size) { index ->
            val url = screenshots[index]
            Padding(end = 20.dp) {
                Box(modifier = Modifier.width(width = itemWidth)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TouchableImage(
                            url = url,
                            width = itemWidth,
                            height = itemHeight,
                            cornerRadius = 10.dp,
                            rippleColor = MaterialTheme.colors.primary
                        ) {
                            onIndexSelected(index)
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            titles[index],
                            style = MaterialTheme.typography.body1.copy(color = Color.White)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .width(itemWidth)
                            .height(itemHeight)
                            .background(
                                Color(
                                    17,
                                    27,
                                    27
                                ).copy(alpha = 0.4f)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Outlined.PlayArrow,
                            "",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
