package com.abhishek101.gamescout.features.preferenceselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewModel
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState
import com.abhishek101.gamescout.design.image.SelectableRemoteImage
import com.abhishek101.gamescout.design.system.ProgressIndicator
import com.abhishek101.gamescout.design.system.SystemUiControlView
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun PlatformSelectionScreen(
    viewModel: PreferenceSelectionViewModel = get(),
    platformSelectionDone: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getPlatforms()
    }

    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.primaryBackground,
        navigationBarColor = ScoutTheme.colors.primaryBackground,
        useDarkIcons = false
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(ScoutTheme.colors.primaryBackground)
            ) {
                item {
                    Text(
                        text = "Platforms",
                        color = ScoutTheme.colors.textOnPrimaryBackground,
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                item {
                    Text(
                        text = "Select the platforms your own",
                        color = ScoutTheme.colors.textOnPrimaryBackground,
                        style = MaterialTheme.typography.body1
                    )
                }

                item {
                    when (viewState) {
                        PreferenceSelectionViewState.Loading ->
                            Box(modifier = Modifier.height(maxHeight)) {
                                ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnPrimaryBackground)
                            }
                        else -> PlatformListView(
                            result = viewState as PreferenceSelectionViewState.Result
                        ) { slug: String, isOwned: Boolean ->
                            viewModel.togglePlatform(
                                platformSlug = slug,
                                isOwned = isOwned
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(75.dp))
                }
            }

            (viewState as? PreferenceSelectionViewState.Result)?.let {
                if (it.ownedPlatformCount > 0) {
                    DoneButtonView(modifier = Modifier.padding(bottom = 15.dp)) {
                        platformSelectionDone()
                    }
                }
            }
        }
    }
}

@Composable
private fun PlatformListView(
    result: PreferenceSelectionViewState.Result,
    togglePlatformSelection: (String, Boolean) -> Unit
) {
    val rows = result.platforms.chunked(2)
    for (row in rows) {
        BoxWithConstraints(modifier = Modifier.padding(top = 10.dp)) {
            val halfWidth = maxWidth / 2 - 20.dp
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (platform in row) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.sizeIn(maxWidth = halfWidth)
                    ) {
                        SelectableRemoteImage(
                            request = platform.imageId,
                            shape = CircleShape,
                            selectionColor = ScoutTheme.colors.textOnPrimaryBackground,
                            isSelected = platform.isOwned!!,
                            imageSize = halfWidth,
                            backgroundColor = ScoutTheme.colors.textOnPrimaryBackground
                        ) {
                            togglePlatformSelection(
                                platform.slug,
                                platform.isOwned!!.not()
                            )
                        }

                        Text(
                            text = platform.name,
                            color = ScoutTheme.colors.textOnPrimaryBackground,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DoneButtonView(
    modifier: Modifier = Modifier,
    onTap: () -> Unit
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = ScoutTheme.colors.secondaryButton,
        contentColor = ScoutTheme.colors.textOnPrimaryBackground
    )

    BoxWithConstraints {
        Button(
            onClick = onTap,
            colors = colors,
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
                .width(maxWidth)
                .height(50.dp)
                .padding(horizontal = 50.dp)
        ) {
            Row {
                Text(
                    text = "Done",
                    color = ScoutTheme.colors.textOnPrimaryBackground,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
