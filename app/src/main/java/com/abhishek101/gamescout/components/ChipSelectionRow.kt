package com.abhishek101.gamescout.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.SelectableChip
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ChipSelectionRow(chipData: Map<String, Boolean>, updatePlatformAsOwned: (String) -> Unit) {
    FlowRow(modifier = Modifier.fillMaxWidth(), crossAxisSpacing = 10.dp) {
        for (data in chipData) {
            SelectableChip(
                selected = data.value,
                selectedColor = MaterialTheme.colors.primary,
                unSelectedColor = MaterialTheme.colors.background,
                selectedBorderColor = MaterialTheme.colors.onBackground,
                unSelectedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                borderWidth = 1.dp,
                cornerShape = MaterialTheme.shapes.large,
                data = data.key,
                selectedTextColor = Color.White,
                unSelectedTextColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
            ) {
                updatePlatformAsOwned(it)
            }
        }
    }
}
