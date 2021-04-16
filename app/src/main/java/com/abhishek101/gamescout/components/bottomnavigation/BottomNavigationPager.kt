package com.abhishek101.gamescout.components.bottomnavigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.theme.GameTrackerTheme

@Composable
fun BottomNavigationPager(
    bottomTabs: List<BottomNavigationTabData>,
    pagerContent: @Composable (Int) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val selectedIndex = remember { mutableStateOf(0) }
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            pagerContent(selectedIndex.value)
        },
        bottomBar = {
            BottomNavigationBar(bottomTabs = bottomTabs, selectedIndex)
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigationBar(
    bottomTabs: List<BottomNavigationTabData>,
    selectedIndex: MutableState<Int>
) {
    BottomAppBar {
        bottomTabs.forEachIndexed { index, tab ->
            val color = if (selectedIndex.value == index) {
                MaterialTheme.colors.onBackground
            } else {
                MaterialTheme.colors.background
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(role = Role.Tab, onClick = { selectedIndex.value = index })
            ) {
                Icon(tab.icon, tab.contentDescription, tint = color)
                Box(Modifier.size(10.dp, 0.dp))
                Text(tab.label, color = color)
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarNormal() {
    val bottomTabs = listOf(
        BottomNavigationTabData(Icons.Outlined.Home, "Home", "Home"),
        BottomNavigationTabData(Icons.Outlined.Search, "Search", "Search"),
        BottomNavigationTabData(Icons.Outlined.Person, "Profile", "Profile"),
    )

    GameTrackerTheme {
        BottomNavigationPager(bottomTabs = bottomTabs, pagerContent = { PreviewContent(index = 0) })
    }
}

@Composable
fun PreviewContent(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (index) {
                    0 -> Color.Red
                    1 -> Color.Green
                    2 -> Color.Blue
                    else -> Color.Black
                }
            )
    )
}
