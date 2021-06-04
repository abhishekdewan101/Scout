package com.abhishek101.gamescout.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PlatformSelectionRow(platforms: Map<String, Boolean>, updatePlatformAsOwned: (String) -> Unit) {
    FlowRow(modifier = Modifier.fillMaxWidth(), crossAxisSpacing = 10.dp) {
        for (platform in platforms) {
            val isSelected = platform.value
            SelectableChip(isSelected, updatePlatformAsOwned, platform)
        }
    }
}

@Composable
fun SelectableChip(
    isSelected: Boolean,
    updatePlatformAsOwned: (String) -> Unit,
    platform: Map.Entry<String, Boolean>
) {
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
            .clickable { updatePlatformAsOwned(platform.key) }
    ) {
        Text(
            text = platform.key,
            color = if (isSelected) Color.White else MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}