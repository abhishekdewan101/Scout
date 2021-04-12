package com.abhishek101.gamescout.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.ui.theme.GameTrackerTheme

enum class AnimatedButtonState {
    IDLE,
    PRESSED
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedButton(minWidth: Dp, maxWidth: Dp, height: Dp) {
    var buttonState by remember(AnimatedButtonState.IDLE) {
        mutableStateOf(AnimatedButtonState.IDLE)
    }

    val transition = updateTransition(targetState = buttonState)

    val color = transition.animateColor(label = "") { state ->
        when (state) {
            AnimatedButtonState.IDLE -> MaterialTheme.colors.secondary
            AnimatedButtonState.PRESSED -> MaterialTheme.colors.surface
        }
    }

    val width = transition.animateDp(label = "") { state ->
        when (state) {
            AnimatedButtonState.IDLE -> maxWidth
            AnimatedButtonState.PRESSED -> minWidth
        }
    }

    Box(
        Modifier
            .width(width.value)
            .height(height) // FIXME: Needs to be dynamic
            .clip(MaterialTheme.shapes.small.copy(CornerSize(percent = 50)))
            .background(color.value)
            .clickable {
                buttonState = when (buttonState) {
                    AnimatedButtonState.IDLE -> AnimatedButtonState.PRESSED
                    AnimatedButtonState.PRESSED -> AnimatedButtonState.IDLE
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(visible = buttonState == AnimatedButtonState.IDLE) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Add, "", tint = Color.White)
                    Text(
                        text = "Add to favorites",
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color.White
                    )
                }
            }
            AnimatedVisibility(visible = buttonState == AnimatedButtonState.PRESSED) {
                Row {
                    Icon(Icons.Filled.Done, "", tint = Color.White)
                }
            }
        }

    }
}

@Preview
@Composable
fun AnimatedButtonPreview() {
    GameTrackerTheme {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            AnimatedButton(minWidth = 50.dp, maxWidth = 200.dp, height = 50.dp)
            AnimatedButton(minWidth = 50.dp, maxWidth = 175.dp, height = 50.dp)
        }
    }
}
