package com.abhishek101.gamescout.features.platformselection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.Platform
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewModel
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState.Loading
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState.Result
import com.abhishek101.gamescout.design.CircularSelectableImage
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.theme.White
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelection(
    viewModel: PreferenceSelectionViewModel = get(),
    setStatusBarColor: (Color, Boolean) -> Unit,
    onPlatformSelectionComplete: () -> Unit
) {

    val viewState = viewModel.viewState.collectAsState()

    val backgroundColor =
        if (MaterialTheme.colors.isLight) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
    val useDarkIcon = MaterialTheme.colors.isLight

    SideEffect {
        setStatusBarColor(backgroundColor, useDarkIcon)
    }

    LaunchedEffect(key1 = viewModel) {
        if (viewState.value is Loading) {
            viewModel.getPlatforms()
        }
    }

    when (viewState.value) {
        Loading -> LoadingIndicator(color = White, backgroundColor = backgroundColor)
        is Result -> PlatformListContent(
            platformList = (viewState.value as Result).platforms,
            ownedCount = (viewState.value as Result).ownedPlatformCount,
            backgroundColor = backgroundColor,
            onPlatformSelected = viewModel::togglePlatform,
            onPlatformSelectionComplete = onPlatformSelectionComplete,
        )
    }
}

@Composable
fun PlatformListContent(
    platformList: List<Platform>,
    ownedCount: Int,
    backgroundColor: Color,
    onPlatformSelected: (String, Boolean) -> Unit,
    onPlatformSelectionComplete: () -> Unit,
) {
    RenderPlatformList(
        backgroundColor,
        ownedCount,
        platformList,
        onPlatformSelected,
        onPlatformSelectionComplete
    )
}

@Composable
private fun RenderPlatformList(
    backgroundColor: Color,
    ownedCount: Int,
    platformList: List<Platform>,
    onPlatformSelected: (String, Boolean) -> Unit,
    onPlatformSelectionComplete: () -> Unit
) {
    Box {
        SafeArea(padding = 15.dp, backgroundColor = backgroundColor) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (ownedCount > 0) 50.dp else 0.dp)
            ) {
                item {
                    Header()
                }
                val chunked = platformList.chunked(2)
                items(chunked.size) { index ->
                    Padding(top = 15.dp) {
                        BoxWithConstraints {
                            val itemWidth = maxWidth / 2 - 10.dp
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (item in chunked[index]) {
                                    CircularSelectableImage(
                                        imageUri = item.imageId,
                                        width = itemWidth,
                                        isSelected = item.isOwned!!,
                                        selectedBorderColor = White
                                    ) {
                                        onPlatformSelected(item.slug, item.isOwned!!.not())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RenderDoneButton(ownedCount, onPlatformSelectionComplete)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun RenderDoneButton(
    ownedCount: Int,
    onPlatformSelectionComplete: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = ownedCount > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clickable {
                        onPlatformSelectionComplete()
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Outlined.Done,
                        "",
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "PLATFORMS",
            style = MaterialTheme.typography.h4,
            color = White,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Select the platforms you own",
            style = MaterialTheme.typography.subtitle1,
            color = White
        )
    }
}
