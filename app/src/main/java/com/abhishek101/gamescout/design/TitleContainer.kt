package com.abhishek101.gamescout.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleContainer(
    title: String,
    hasViewMore: Boolean,
    onViewMoreClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        TitleRow(title = title, onViewMoreClicked = onViewMoreClicked, hasViewMore = hasViewMore)
        Spacer(modifier = Modifier.height(15.dp))
        content()
    }
}

@Composable
fun TitleRow(title: String, onViewMoreClicked: () -> Unit, hasViewMore: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            title,
            style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onBackground)
        )
        if (hasViewMore) {
            IconButton(onClick = onViewMoreClicked) {
                Icon(Icons.Outlined.MoreVert, "More", tint = MaterialTheme.colors.onBackground)
            }
        }
    }
}

@Preview
@Composable
fun PreviewTitledContainer() {
    Column {
        TitleContainer(title = "Title", hasViewMore = true, onViewMoreClicked = { }) {

        }

        TitleContainer(title = "Title", hasViewMore = false, onViewMoreClicked = { }) {

        }
    }
}