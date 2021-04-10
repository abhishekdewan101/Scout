package com.abhishek101.gamescout.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange,
    primaryVariant = DarkOrange,
    secondary = Blue,
    secondaryVariant = Blue,
    surface = Green,
    onSurface = Mustard,
    background = Black,
    onPrimary = OffWhite,
    error = Crimson,
    onError = OffWhite,
    onBackground = OffWhite,
)

private val LightColorPalette = lightColors(
    primary = Orange,
    primaryVariant = DarkOrange,
    secondary = Blue,
    secondaryVariant = Blue,
    surface = Green,
    onSurface = Mustard,
    background = Black,
    onPrimary = OffWhite,
    error = Crimson,
    onError = OffWhite,
    onBackground = OffWhite,
)

@Composable
fun GameTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
