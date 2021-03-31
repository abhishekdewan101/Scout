package com.abhishek101.gametracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

@Composable
fun ListItem(isSelected: Boolean, data: String, onItemTapped: (() -> Unit) = {}) {

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
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .sizeIn(minHeight = 150.dp, minWidth = 150.dp)
            ) {
                Text(
                    data,
                    style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onSurface),
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ListItemSelected() {
    GameTrackerTheme {
        ListItem(isSelected = true, data = "Hack and Slash/ Beat em Up!")
    }
}

@Preview
@Composable
fun ListItemNotSelected() {
    GameTrackerTheme {
        ListItem(isSelected = false, data = "Hack and Slash/ Beat em Up!")
    }
}