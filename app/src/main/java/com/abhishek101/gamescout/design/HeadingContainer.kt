package com.abhishek101.gamescout.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HeadingContainer(
    heading: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            heading,
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        content()
    }
}
