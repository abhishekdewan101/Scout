package com.abhishek101.gamescout.design.image

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectableRemoteImage(
    request: Any,
    imageSize: Dp,
    scaleImageFactor: Float = 0.6f,
    shape: CornerBasedShape,
    backgroundColor: Color = Color.Transparent,
    isSelected: Boolean = false,
    borderStrokeWidth: Dp = 3.dp,
    selectionColor: Color,
    onTap: () -> Unit
) {
    val borderStroke = BorderStroke(
        width = if (isSelected) borderStrokeWidth else 0.dp,
        color = if (isSelected) selectionColor else Color.Transparent
    )

    Box(
        modifier = Modifier
            .size(size = imageSize)
            .border(border = borderStroke, shape = shape)
            .padding(all = 10.dp)
            .clip(shape = shape)
            .clickable { onTap() }
            .background(color = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RemoteImage(
                data = request,
                contentDescription = "selectable image",
                modifier = Modifier.size(
                    imageSize.times(scaleImageFactor)
                )
            )
        }
    }
}
