package com.abhishek101.gametracker.ui.features.platformSelection

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.abhishek101.core.db.Platform
import com.abhishek101.gametracker.ui.components.imageListItem.ImageListItem
import com.abhishek101.gametracker.ui.components.imageListItem.toImageListItemData
import com.abhishek101.gametracker.ui.components.navigation.LocalMainNavController
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorDestinations.GenreSelectionScreen
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme
import org.koin.androidx.compose.get

@ExperimentalAnimationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelection(viewModel: PlatformSelectionViewModel = get()) {

    val isLoading = viewModel.isLoading
    val platformList = viewModel.platforms
    val navController = LocalMainNavController.current

    PlatformSelectionContent(
        isLoading = isLoading.value,
        platformList = platformList.value,
        onPlatformSelected = { platform: Platform, isOwned: Boolean ->
            viewModel.updateOwnedPlatform(platform, isOwned)
        },
        getOwnedPlatformCount = viewModel::getOwnedPlatformCount,
        navigateToGenreScreen = {
            navController.popBackStack()
            navController.navigate(GenreSelectionScreen.name)
        }
    )
}

@ExperimentalAnimationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelectionContent(
    isLoading: Boolean,
    platformList: List<Platform>,
    onPlatformSelected: (Platform, Boolean) -> Unit,
    getOwnedPlatformCount: () -> Int,
    navigateToGenreScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp)) {
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .semantics {
                                testTag = "loadingBar"
                            },
                        color = MaterialTheme.colors.primary
                    )
                } else {
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
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 15.dp, end = 15.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            AnimatedVisibility(visible = getOwnedPlatformCount() > 0) {
                Button(
                    onClick = navigateToGenreScreen,
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                ) {
                    Text("Done", color = MaterialTheme.colors.onPrimary)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(device = Devices.PIXEL_4_XL, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun PlatformSelectionScreenLoadingState() {
    GameTrackerTheme {
        PlatformSelectionContent(
            isLoading = true,
            platformList = listOf(),
            onPlatformSelected = { _, _ -> },
            getOwnedPlatformCount = { return@PlatformSelectionContent 0 },
            navigateToGenreScreen = {}
        )
    }
}

@ExperimentalAnimationApi
@Preview(device = Devices.PIXEL_4_XL, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun PlatformSelectionScreenListState() {
    GameTrackerTheme {
        PlatformSelectionContent(
            isLoading = false,
            platformList = listOf(Platform(0, "xbox", "xbox series x", 1080, 1080, "pleu", true)),
            onPlatformSelected = { _, _ -> },
            getOwnedPlatformCount = { return@PlatformSelectionContent 1 },
            navigateToGenreScreen = {}
        )
    }
}
