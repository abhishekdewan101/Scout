package com.abhishek101.gamescout.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedSearchBar(maxWidth: Dp, animationStarted: (Boolean) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var fullyExpanded by remember { mutableStateOf(false) }
    var searchbarWidth by remember { mutableStateOf(0f) }
    var textState by remember { mutableStateOf(TextFieldValue()) }
    val animatedSearchBarWidth by animateFloatAsState(
        targetValue = searchbarWidth, animationSpec = tween(durationMillis = 500)
    ) {
        fullyExpanded = it != 0f
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        AnimatedVisibility(visible = !expanded) {
            Column(modifier = Modifier.height(56.dp), verticalArrangement = Arrangement.Center) {
                Icon(Icons.Outlined.Search, "", modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        animationStarted(true)
                        expanded = true
                        searchbarWidth = (maxWidth - 32.dp).value
                    })
            }
        }
        AnimatedVisibility(visible = expanded) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = fullyExpanded) {
                    Icon(Icons.Outlined.ArrowBack, "", modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            animationStarted(false)
                            expanded = false
                            searchbarWidth = 0f
                        })
                }
                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier
                        .width(animatedSearchBarWidth.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.onBackground,
                        focusedBorderColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.primary,
                        textColor = MaterialTheme.colors.onBackground,
                        trailingIconColor = MaterialTheme.colors.primary
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnimatedSearchBar() {
    AnimatedSearchBar(maxWidth = 1000.dp) {

    }
}