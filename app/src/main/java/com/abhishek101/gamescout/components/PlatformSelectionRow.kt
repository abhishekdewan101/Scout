package com.abhishek101.gamescout.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlatformSelectionRow(platforms: Map<String, Boolean>, updatePlatformAsOwned: (String) -> Unit) {
    val platformList = platforms.keys.toList()
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(platformList.size) {
            val isSelected = platforms[platformList[it]]!!
            Surface(
                color = when {
                    isSelected -> MaterialTheme.colors.primary
                    else -> MaterialTheme.colors.background.copy(alpha = 0.5f)
                },
                contentColor = MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colors.onBackground,
                        shape = CircleShape.copy(
                            CornerSize(5.dp)
                        )
                    )
                    .clickable { updatePlatformAsOwned(platformList[it]) }
            ) {
                Text(
                    text = platformList[it],
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}