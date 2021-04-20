package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.theme.GameTrackerTheme
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
val LocalBottomSheetState = compositionLocalOf<BottomSheetState> {
    error("No bottom sheet state provided")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContainer(sheetContent: @Composable () -> Unit, content: @Composable () -> Unit) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    CompositionLocalProvider(LocalBottomSheetState provides bottomSheetScaffoldState.bottomSheetState) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = { sheetContent() },
            sheetPeekHeight = 0.dp
        ) {
            content()
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewBottomSheetContainer() {
    val coroutine = rememberCoroutineScope()
    GameTrackerTheme {
        BottomSheetContainer(sheetContent = {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                Text(
                    "Something is happening",
                    style = MaterialTheme.typography.h1.copy(color = Color.White)
                )
            }
        }) {
            val bottomSheetState = LocalBottomSheetState.current
            Column {
                Button(onClick = {
                    coroutine.launch {
                        if (bottomSheetState.isExpanded) {
                            bottomSheetState.collapse()
                        } else {
                            bottomSheetState.expand()
                        }
                    }
                }) {
                    Text(text = "Expand/Collapse Bottom Sheet")
                }
            }
        }
    }
}