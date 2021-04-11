package com.abhishek101.gamescout.ui.features.platformselection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.abhishek101.core.db.Platform
import com.abhishek101.gamescout.ui.components.listItems.ImageListItem
import com.abhishek101.gamescout.ui.components.listItems.toImageListItemData
import com.abhishek101.gamescout.ui.components.navigation.LocalMainNavController
import com.abhishek101.gamescout.ui.components.navigation.MainNavigatorDestinations.GenreSelectionScreen
import com.abhishek101.gamescout.ui.theme.GameTrackerTheme
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelection(viewModel: PlatformSelectionViewModel = get()) {

    val isLoading = viewModel.isLoading
    val platformList = viewModel.platforms
    val navController = LocalMainNavController.current

    PlatformSelectionScaffold(
        isLoading = isLoading.value,
        platformList = platformList.value,
        onPlatformSelected = { platform: Platform, isOwned: Boolean ->
            viewModel.updateOwnedPlatform(platform, isOwned)
        },
        getOwnedPlatformCount = viewModel::getOwnedPlatformCount,
        navigateForward = {
            navController.popBackStack()
            navController.navigate(GenreSelectionScreen.name)
        }
    )
}

@Composable
fun PlatformSelectionScaffold(
    isLoading: Boolean,
    platformList: List<Platform>,
    onPlatformSelected: (Platform, Boolean) -> Unit,
    getOwnedPlatformCount: () -> Int,
    navigateForward: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { PlatformSelectionHeader() },
        content = {
            PlatformSelectionContent(
                isLoading = isLoading,
                platformList = platformList,
                onPlatformSelected = onPlatformSelected
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            PlatformSelectionFab(
                ownedPlatformCount = getOwnedPlatformCount(),
                navigateForward = navigateForward
            )
        }
    )
}

@Composable
fun PlatformSelectionFab(ownedPlatformCount: Int, navigateForward: () -> Unit) {
    if (ownedPlatformCount > 0) {
        FloatingActionButton(
            onClick = navigateForward,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(Icons.Outlined.Done, "Done", tint = MaterialTheme.colors.onPrimary)
        }
    }
}

@Composable
fun PlatformSelectionHeader() {
    Box(modifier = Modifier.padding(top = 15.dp, start = 15.dp)) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                "Owned Platforms",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                "We will use these platforms to tailor your search results",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun PlatformSelectionContent(
    isLoading: Boolean,
    platformList: List<Platform>,
    onPlatformSelected: (Platform, Boolean) -> Unit
) {
    if (isLoading) {
        PlatformSelectionLoading()
    } else {
        PlatformSelectionListContent(platformList, onPlatformSelected)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelectionListContent(
    platformList: List<Platform>,
    onPlatformSelected: (Platform, Boolean) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count = 2),
        modifier = Modifier.padding(top = 20.dp)
    ) {
        items(platformList.size) { index ->
            platformList[index].apply {
                ImageListItem(
                    isSelected = this.isOwned ?: false,
                    data = this.toImageListItemData(),
                    imageOnly = true
                ) {
                    onPlatformSelected(this, this.isOwned?.not() ?: false)
                }
            }
        }
    }
}

@Composable
fun PlatformSelectionLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = 20.dp)
                .semantics {
                    testTag = "loadingBar"
                },
            color = MaterialTheme.colors.primary
        )
    }
}

@Preview
@Composable
fun PlatformSelectionWithData() {
    GameTrackerTheme {
        PlatformSelectionScaffold(
            isLoading = false,
            platformList = platformTestData,
            onPlatformSelected = { _, _ -> },
            getOwnedPlatformCount = { return@PlatformSelectionScaffold 2 }
        ) {
        }
    }
}

@Preview
@Composable
fun PlatformSelectionWithNoData() {
    GameTrackerTheme {
        PlatformSelectionScaffold(
            isLoading = true,
            platformList = emptyList(),
            onPlatformSelected = { _, _ -> },
            getOwnedPlatformCount = { return@PlatformSelectionScaffold 0 }
        ) {
        }
    }
}

val platformTestData = listOf(
    Platform(1, "slug", "name", 3, "url", isOwned = false),
    Platform(1, "slug", "name1", 3, "url", isOwned = true),
    Platform(1, "slug", "name2", 3, "url", isOwned = true),
)
