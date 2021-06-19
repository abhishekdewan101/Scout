package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.abhishek101.gamescout.R

// TODO: Add documentation
@Composable
fun TouchableImage(
    url: String,
    width: Dp,
    height: Dp,
    cornerRadius: Dp? = null,
    backgroundColor: Color? = null,
    rippleColor: Color? = null,
    loadingColor: Color? = null,
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
                    interactionSource = remember { MutableInteractionSource() }
                ) { onTouch() }
            } else {
                Modifier.clickable { onTouch() }
            }
        )

    Box(modifier) {
        CoilImage(
            data = url,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width, height),
            loading = {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = loadingColor ?: MaterialTheme.colors.primary
                    )
                }
            },
            error = {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val image = painterResource(id = R.drawable.error_outline_24)
                    Icon(painter = image, "")
                }
            }
        )
    }
}
