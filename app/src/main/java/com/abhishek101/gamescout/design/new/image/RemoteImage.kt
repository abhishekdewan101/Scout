package com.abhishek101.gamescout.design.new.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.theme.ScoutTheme
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun RemoteImage(
    request: Any,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Inside,
    fadeIn: Boolean = true,
    error: @Composable (() -> Unit)? = null,
    loading: @Composable (() -> Unit)? = null
) {
    val remotePainter = rememberCoilPainter(
        request = request,
        fadeIn = fadeIn
    )

    Box {
        Image(
            painter = remotePainter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
        when (remotePainter.loadState) {
            is ImageLoadState.Loading -> {
                ImageLoadingView(loading = loading)
            }
            is ImageLoadState.Error -> {
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
        val painter = rememberCoilPainter(request = R.drawable.error_outline_24)
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = ScoutTheme.colors.textOnPrimaryBackground)
        }
    }
}