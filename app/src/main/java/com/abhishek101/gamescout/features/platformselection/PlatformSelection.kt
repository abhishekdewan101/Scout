package com.abhishek101.gamescout.features.platformselection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.db.Platform
import com.abhishek101.core.utils.buildImageString
import com.abhishek101.gamescout.design.CircularSelectableImage
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.theme.White
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelection(
    viewModel: PlatformSelectionViewModel = get(),
    setStatusBarColor: (Color, Boolean) -> Unit,
    onPlatformSelectionComplete: () -> Unit
) {

    val isLoading = viewModel.isLoading.value
    val platformList = viewModel.platforms.value
    val ownedCount = viewModel.getOwnedPlatformCount()

    val backgroundColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
    val useDarkIcon = MaterialTheme.colors.isLight

    SideEffect {
        setStatusBarColor(backgroundColor, useDarkIcon)
    }

    PlatformSelectionList(
        isLoading = isLoading,
        platformList = platformList,
        ownedCount = ownedCount,
        backgroundColor = backgroundColor,
        onPlatformSelected = viewModel::updateOwnedPlatform,
        onPlatformSelectionComplete = onPlatformSelectionComplete,
    )
}

@Composable
fun PlatformSelectionList(
    isLoading: Boolean,
    platformList: List<Platform>,
    ownedCount: Int,
    backgroundColor: Color,
    onPlatformSelected: (String, Boolean) -> Unit,
    onPlatformSelectionComplete: () -> Unit,
) {
    SafeArea(padding = 15.dp, backgroundColor = backgroundColor) {

        if (isLoading) {
            LoadingIndicator(color = White)
        } else {
            Box {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        PlatformSelectionHeader()
                    }
                    val chunked = platformList.chunked(2)
                    items(chunked.size) { index ->
                        Padding(top = 15.dp) {
                            BoxWithConstraints {
                                val itemWidth = maxWidth / 2 - 10.dp
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    for (item in chunked[index]) {
                                        CircularSelectableImage(
                                            imageUri = buildImageString(item.imageId),
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
                RenderDoneButton(ownedCount, onPlatformSelectionComplete)
            }
        }
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
            Padding(bottom = 15.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colors.primary)
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
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlatformSelectionHeader() {
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
