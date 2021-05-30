package com.abhishek101.gamescout.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.theme.GameTrackerTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedSearchBar(title: String) {
    var expanded by remember { mutableStateOf(false) }
    var searchState by remember { mutableStateOf(TextFieldValue()) }
    var searchbarWidth by remember { mutableStateOf(0f) }

    AppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        elevation = 0.dp,
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            AnimatedVisibility(visible = expanded) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { expanded = false }) {
                        Icon(Icons.Outlined.ArrowBack, "", tint = MaterialTheme.colors.onBackground)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = searchState,
                        onValueChange = { searchState = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = MaterialTheme.colors.primary,
                            backgroundColor = MaterialTheme.colors.onBackground,
                            focusedBorderColor = MaterialTheme.colors.primary,
                            cursorColor = MaterialTheme.colors.primary,
                            textColor = MaterialTheme.colors.onBackground,
                            trailingIconColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedVisibility(visible = !expanded) {
                    Text(
                        title, style = MaterialTheme.typography.h4.copy(
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                }

                AnimatedVisibility(visible = !expanded) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Outlined.Search, "", tint = MaterialTheme.colors.onBackground)
                    }
                }
            }
        }
    }
}

//
// Row(
//     modifier = Modifier.fillMaxWidth(),
//     horizontalArrangement = Arrangement.End
// ) {
//     AnimatedVisibility(visible = !expanded) {
//         Column(modifier = Modifier.height(56.dp), verticalArrangement = Arrangement.Center) {
//             Icon(Icons.Outlined.Search, "", modifier = Modifier
//                 .size(32.dp)
//                 .clickable {
//                     animationStarted(true)
//                     expanded = true
//                     searchbarWidth = (maxWidth - 32.dp).value
//                 })
//         }
//     }
//     AnimatedVisibility(visible = expanded) {
//         Row(
//             horizontalArrangement = Arrangement.Start,
//             verticalAlignment = Alignment.CenterVertically
//         ) {
//             AnimatedVisibility(visible = fullyExpanded) {
//                 Icon(Icons.Outlined.ArrowBack, "", modifier = Modifier
//                     .size(32.dp)
//                     .clickable {
//                         animationStarted(false)
//                         expanded = false
//                         searchbarWidth = 0f
//                     })
//             }
//             OutlinedTextField(
//                 value = textState,
//                 onValueChange = { textState = it },
//                 modifier = Modifier
//                     .width(animatedSearchBarWidth.dp),
//                 colors = TextFieldDefaults.outlinedTextFieldColors(
//                     unfocusedBorderColor = MaterialTheme.colors.primary,
//                     backgroundColor = MaterialTheme.colors.onBackground,
//                     focusedBorderColor = MaterialTheme.colors.primary,
//                     cursorColor = MaterialTheme.colors.primary,
//                     textColor = MaterialTheme.colors.onBackground,
//                     trailingIconColor = MaterialTheme.colors.primary
//                 ),
//             )
//         }
//     }
// }

@Composable
fun AppBar(
    backgroundColor: Color,
    contentColor: Color,
    elevation: Dp,
    contentPadding: PaddingValues,
    shape: Shape,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(56.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview
@Composable
fun PreviewAnimatedSearchBar() {
    GameTrackerTheme {
        AnimatedSearchBar("Search")
    }
}