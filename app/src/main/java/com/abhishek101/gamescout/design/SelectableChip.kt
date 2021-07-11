package com.abhishek101.gamescout.design

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectableChip(
    selected: Boolean,
    selectedColor: Color,
    unSelectedColor: Color,
    selectedBorderColor: Color,
    unSelectedBorderColor: Color,
    borderWidth: Dp,
    cornerShape: CornerBasedShape,
    data: String,
    selectedTextColor: Color,
    unSelectedTextColor: Color,
    onClickChip: (String) -> Unit,
) {
    val surfaceColor = when {
        selected -> selectedColor
        else -> unSelectedColor
    }
    val borderColor = when {
        selected -> selectedBorderColor
        else -> unSelectedBorderColor
    }
    val surfaceModifier = Modifier
        .padding(horizontal = 6.dp)
        .border(borderWidth, borderColor, cornerShape)
        .clickable { onClickChip(data) }

    val textColor = when {
        selected -> selectedTextColor
        else -> unSelectedTextColor
    }

    val textModifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

    Surface(color = surfaceColor, shape = cornerShape, modifier = surfaceModifier) {
        Text(
            text = data,
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = textModifier
        )
    }
}
