package com.abhishek101.gametracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFea5e33),
    primaryVariant = Color(0xFFf9cfc2),
    secondary = Color(0xFF5b1709),
    secondaryVariant = Color(0xFF5b1709),
    surface = Color(0xFFcec8c5),
    onSurface = Color(0xFF5c5855),
    background = Color(0xFF0e0d0d),
    onPrimary = Color(0xFF0FFFFFF),
    error = Color(0xFFf92d07),
    onError = Color(0xFFf92d07),
    onBackground = Color(0xFFffffff),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFea5e33),
    primaryVariant = Color(0xFFe14220),
    secondary = Color(0xFF5b1709),
    secondaryVariant = Color(0xFFceb9b5),
    surface = Color(0xFFcec8c5),
    onSurface = Color(0xFF5c5855),
    background = Color(0xFFffffff),
    onPrimary = Color(0xFF0FFFFFF),
    error = Color(0xFFf92d07),
    onError = Color(0xFFf92d07),
    onBackground = Color(0xFF0e0d0d),
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
