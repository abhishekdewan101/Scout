package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Composable that allows you add a padding around your content without having to deal with the
 * modifiers
 */
//TODO: Add documentation
@Composable
fun SafeArea(
    padding: Dp,
    backgroundColor: Color = MaterialTheme.colors.background,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(padding)
    ) {
        content()
    }
}