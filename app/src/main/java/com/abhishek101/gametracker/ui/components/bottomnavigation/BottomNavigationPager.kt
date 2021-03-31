package com.abhishek101.gametracker.ui.components.bottomnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme
import timber.log.Timber

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
    Timber.d("Selected Index - ${selectedIndex.value}")

    BottomAppBar {
        bottomTabs.forEachIndexed { index, tab ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                IconButton(onClick = { selectedIndex.value = index }) {
                    Icon(tab.icon, tab.contentDescription)
                }
                AnimatedVisibility(visible = (selectedIndex.value == index)) {
                    Text(tab.label)
                }
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