package com.abhishek101.gamescout.design

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.R
import com.google.accompanist.coil.CoilImage

@Composable
fun CircularSelectableImage(
    @DrawableRes imageId: Int,
    label: String? = null,
    isSelected: Boolean = false,
    onSelected: () -> Unit
) {
    val borderStroke = BorderStroke(
        if (isSelected) 3.0.dp else 0.dp,
        if (isSelected) Color(240, 115, 101) else Color.Transparent
    )

    Box(
        modifier = Modifier
            .size(175.dp)
            .border(borderStroke, RoundedCornerShape(50))
            .clickable { onSelected() }
            .padding(10.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                data = imageId,
                contentDescription = "",
                modifier = Modifier.size(50.dp, 50.dp),
                fadeIn = true,
                loading = {
                    Icon(
                        Icons.Outlined.Image,
                        "image loading",
                        tint = MaterialTheme.colors.onBackground
                    )
                },
                error = {
                    Icon(
                        Icons.Outlined.Error,
                        "error loading image",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            )
            if (label != null) {
                Padding(top = 10.dp) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.body1.copy(Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCircularSelectableImage() {
    CircularSelectableImage(R.drawable.hack_slash, "Hack and Slash/Adventure", true) {}
}