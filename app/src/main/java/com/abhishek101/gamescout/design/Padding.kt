package com.abhishek101.gamescout.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Padding(
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.padding(
            start = start,
            end = end,
            top = top,
            bottom = bottom
        )
    ) {
        content()
    }
}