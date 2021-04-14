package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.coil.CoilImage

@Composable
fun TouchableImage(
    url: String,
    width: Dp,
    height: Dp,
    cornerRadius: Dp?,
    backgroundColor: Color?,
    rippleColor: Color?,
    onTouch: () -> Unit
) {

    val modifier = Modifier
        .then(
            if (cornerRadius != null) {
                Modifier.clip(RoundedCornerShape(cornerRadius))
            } else Modifier
        )
        .then(
            if (backgroundColor != null) {
                Modifier.background(backgroundColor)
            } else Modifier
        )
        .then(
            if (rippleColor != null) {
                Modifier.clickable(
                    indication = rememberRipple(color = rippleColor),
                    interactionSource = remember { MutableInteractionSource() }) { onTouch() }
            } else {
                Modifier.clickable { onTouch() }
            }
        )

    Box(modifier) {
        CoilImage(
            data = url,
            contentDescription = "",
            modifier = Modifier
                .size(width, height)
        )
    }
}