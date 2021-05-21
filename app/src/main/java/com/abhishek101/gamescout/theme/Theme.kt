package com.abhishek101.gamescout.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple,
    primaryVariant = PurpleDark,
    secondary = Beige,
    secondaryVariant = BeigeDark,
    surface = Black,
    onSurface = White,
    background = Black,
    onPrimary = White,
    error = Red,
    onError = White,
    onBackground = White,
)
private val LightColorPalette = lightColors(
    primary = Purple,
    primaryVariant = PurpleLight,
    onPrimary = White,
    secondary = Beige,
    secondaryVariant = BeigeLight,
    surface = White,
    onSurface = Black,
    background = White,
    error = Red,
    onError = White,
    onBackground = Black,
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
