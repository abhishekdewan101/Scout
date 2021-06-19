package com.abhishek101.gamescout.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

// TODO: Refactor this properly
@Composable
fun CoilImage(
    data: Any,
    modifier: Modifier,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
    fadeIn: Boolean = true,
    error: @Composable () -> Unit,
    loading: @Composable () -> Unit
) {
    val painter = rememberCoilPainter(
        data,
        fadeIn = fadeIn
    )

    Box {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
        when (painter.loadState) {
            is ImageLoadState.Loading -> {
                loading()
            }
            is ImageLoadState.Error -> {
                error()
            }
        }
    }
}
