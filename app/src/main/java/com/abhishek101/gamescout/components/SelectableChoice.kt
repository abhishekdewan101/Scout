package com.abhishek101.gamescout.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectableChoice(
    isSelected: Boolean,
    text: String,
    selectionColor: Color,
    backgroundColor: Color,
    onPillTapped: () -> Unit
) {
    Surface(
        color = when {
            isSelected -> backgroundColor.copy(alpha = 0.5f)
            else -> backgroundColor
        },
        contentColor = when {
            isSelected -> selectionColor
            else -> Color.White
        },
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .border(
                2.dp,
                when {
                    isSelected -> selectionColor
                    else -> Color.White
                },
                shape = CircleShape.copy(
                    CornerSize(5.dp)
                )
            )
            .clickable { onPillTapped() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun SelectableChoicePreview() {
    LazyRow {
        item {
            SelectableChoice(
                isSelected = true, text = "PS4", Color(203, 112, 209),
                Color.Transparent
            ) {
            }
        }
        item {
            SelectableChoice(
                isSelected = false,
                text = "Xbox",
                Color(203, 112, 209),
                Color.Transparent
            ) {
            }
        }
    }
}
