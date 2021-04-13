package com.abhishek101.gamescout.components.listItems

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.core.utils.buildImageString
import com.abhishek101.gamescout.theme.GameTrackerTheme
import com.google.accompanist.coil.CoilImage

@Composable
fun ImageListItem(
    isSelected: Boolean,
    data: ImageListItemData,
    imageOnly: Boolean = false,
    onItemTapped: (() -> Unit) = {}
) {

    val borderStroke = BorderStroke(
        if (isSelected) 3.0.dp else 0.dp,
        if (isSelected) MaterialTheme.colors.primary else Color.Transparent
    )

    Box(modifier = Modifier.padding(10.dp)) {
        OutlinedButton(
            onClick = onItemTapped,
            border = borderStroke,
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                CoilImage(
                    data = buildImageString(data.imageUrl),
                    contentDescription = "",
                    modifier = Modifier.size(150.dp, 150.dp),
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
                if (!imageOnly) {
                    Text(
                        data.title,
                        style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onSurface),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CircularImageListItemNotSelected() {
    GameTrackerTheme {
        ImageListItem(isSelected = false, previewCircularImageListItemData)
    }
}

@Preview
@Composable
fun CircularImageListItemSelected() {
    GameTrackerTheme {
        ImageListItem(isSelected = true, previewCircularImageListItemData)
    }
}
