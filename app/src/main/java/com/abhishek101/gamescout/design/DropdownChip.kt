package com.abhishek101.gamescout.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun <T> DropdownChip(
    data: Map<T, String>,
    chipBackgroundColor: Color,
    cornerShape: CornerBasedShape,
    initialValue: T,
    textColor: Color,
    iconColor: Color,
    dropDownIconLess: ImageVector,
    dropDownIconMore: ImageVector,
    dropDownBackgroundColor: Color,
    dropDownTextColor: Color,
    dropDownItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(data[initialValue]!!) }

    val icon = if (expanded)
        dropDownIconLess
    else
        dropDownIconMore

    Column {
        Surface(
            color = chipBackgroundColor,
            shape = cornerShape,
            modifier = Modifier.clickable { expanded = !expanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(selectedText, color = textColor)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    icon,
                    "",
                    tint = iconColor
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(dropDownBackgroundColor)
        ) {
            data.keys.forEach {
                val label = data[it]!!
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = !expanded
                        dropDownItemSelected(it)
                    }
                ) {
                    Text(text = label, color = dropDownTextColor)
                }
            }
        }
    }
}
