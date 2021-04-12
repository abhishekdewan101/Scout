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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.ui.theme.GameTrackerTheme

enum class AnimatedButtonState {
    EXPANDED,
    SHRUNK
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CollapsableButton(
    initialWidth: Dp,
    initialColor: Color,
    initialIcon: ImageVector,
    initialLabel: String?,
    initialBorderRadius: Dp,
    finalWidth: Dp,
    finalColor: Color,
    finalIcon: ImageVector,
    height: Dp,
    onClick: () -> Unit
) {
    var buttonState by remember(AnimatedButtonState.EXPANDED) {
        mutableStateOf(AnimatedButtonState.EXPANDED)
    }

    val transition = updateTransition(targetState = buttonState, label = "")

    val color = transition.animateColor(label = "") { state ->
        when (state) {
            AnimatedButtonState.EXPANDED -> finalColor
            AnimatedButtonState.SHRUNK -> initialColor
        }
    }

    val width = transition.animateDp(label = "") { state ->
        when (state) {
            AnimatedButtonState.EXPANDED -> initialWidth
            AnimatedButtonState.SHRUNK -> finalWidth
        }
    }

    val clipShape = when (buttonState) {
        AnimatedButtonState.SHRUNK -> MaterialTheme.shapes.small.copy(CornerSize(50))
        AnimatedButtonState.EXPANDED -> RoundedCornerShape(initialBorderRadius)
    }

    Box(
        Modifier
            .width(width.value)
            .height(height) // FIXME: Needs to be dynamic
            .clip(clipShape)
            .background(color.value)
            .clickable {
                buttonState = when (buttonState) {
                    AnimatedButtonState.EXPANDED -> AnimatedButtonState.SHRUNK
                    AnimatedButtonState.SHRUNK -> AnimatedButtonState.EXPANDED
                }
                onClick()
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(visible = buttonState == AnimatedButtonState.EXPANDED) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(initialIcon, "", tint = Color.White)
                    initialLabel?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(start = 5.dp),
                            color = Color.White
                        )
                    }
                }
            }
            AnimatedVisibility(visible = buttonState == AnimatedButtonState.SHRUNK) {
                Row {
                    Icon(finalIcon, "", tint = Color.White)
                }
            }
        }

    }
}

@Preview
@Composable
fun AnimatedButtonPreview() {
    GameTrackerTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CollapsableButton(
                initialWidth = 200.dp,
                initialColor = MaterialTheme.colors.secondary,
                initialIcon = Icons.Outlined.Add,
                initialLabel = "Add to favorites",
                initialBorderRadius = 10.dp,
                height = 50.dp,
                finalWidth = 50.dp,
                finalColor = MaterialTheme.colors.surface,
                finalIcon = Icons.Outlined.Done
            ) {

            }
        }
    }
}
