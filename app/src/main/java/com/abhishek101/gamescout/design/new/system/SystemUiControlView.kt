package com.abhishek101.gamescout.design.new.system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.abhishek101.gamescout.theme.DebugColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * System Ui Controller is a view that is capable of changing the status bar and navigation bar colors
 *
 * The default is a yellow color that allows it to be easily debuggable if the developer forgets to provide a color.
 */

@Composable
fun SystemUiControlView(
    statusBarColor: Color = DebugColor,
    navigationBarColor: Color = DebugColor,
    useDarkIcons: Boolean = false,
    content: @Composable () -> Unit
) {
    val uiController = rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
        uiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = useDarkIcons
        )
    }
    content()
}
