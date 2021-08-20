package com.abhishek101.gamescout.design.image

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.design.system.ProgressIndicator
import com.abhishek101.gamescout.theme.ScoutTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@ExperimentalCoilApi
@Composable
fun RemoteImage(
    data: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Inside,
    fadeIn: Boolean = true,
    error: @Composable (() -> Unit)? = null,
    loading: @Composable (() -> Unit)? = null
) {
    val remotePainter = rememberImagePainter(
        data = data,
        builder = {
            crossfade(fadeIn)
        }
    )

    Box(modifier = modifier) {
        Image(
            painter = remotePainter,
            modifier = Modifier.fillMaxSize(),
            contentDescription = contentDescription,
            contentScale = contentScale
        )
        when (remotePainter.state) {
            is ImagePainter.State.Loading -> {
                ImageLoadingView(loading = loading)
            }
            is ImagePainter.State.Error -> {
                ImageErrorView(error = error)
            }
        }
    }
}

@Composable
private fun ImageErrorView(error: @Composable (() -> Unit)?) {
    if (error != null) {
        error()
    } else {
        val drawable =
            AppCompatResources.getDrawable(LocalContext.current, R.drawable.error_outline_24)
        val painter = rememberDrawablePainter(drawable = drawable)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentDescription = "error downloading image",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ImageLoadingView(loading: @Composable (() -> Unit)?) {
    if (loading != null) {
        loading()
    } else {
        ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
    }
}
