package com.abhishek101.gamescout.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// region Scout Color Definitions
private val LightColorPalette = ScoutColors(
    primaryBackground = BackgroundPrimary,
    progressIndicatorOnPrimaryBackground = White,
    textOnPrimaryBackground = White,
    secondaryButton = RedLight,
    isDarkTheme = false
)

private val DarkColorPalette = ScoutColors(
    primaryBackground = BackgroundPrimary,
    progressIndicatorOnPrimaryBackground = White,
    textOnPrimaryBackground = White,
    secondaryButton = RedLight,
    isDarkTheme = true
)

data class ScoutColors(
    val primaryBackground: Color,
    val progressIndicatorOnPrimaryBackground: Color,
    val textOnPrimaryBackground: Color,
    var secondaryButton: Color,
    val isDarkTheme: Boolean
)

fun debugColor(isDarkTheme: Boolean, debugColor: Color = DebugColor) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !isDarkTheme
)

// endregion

// region ScoutTheme Composable

private val LocalScoutColors = staticCompositionLocalOf<ScoutColors> {
    error("No scout colors provided")
}

/**
 * Scout theme static access instance
 */
object ScoutTheme {
    val colors: ScoutColors
        @Composable
        get() = LocalScoutColors.current
}

/**
 * ScoutTheme composable that provides access to the ScoutColors
 *
 * Note: MaterialTheme has debug colors to allow easy debug where color
 * theming hasn't been applied and is using material theme colors
 */
@Composable
fun ScoutTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) DarkColorPalette else LightColorPalette
    ProvideScoutTheme(colors = colors) {
        MaterialTheme(
            colors = debugColor(isDarkTheme = isDarkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
private fun ProvideScoutTheme(colors: ScoutColors, content: @Composable () -> Unit) {
    val colorPalette = remember {
        colors
    }
    CompositionLocalProvider(
        LocalScoutColors provides colorPalette,
        content = content
    )
}

// endregion
