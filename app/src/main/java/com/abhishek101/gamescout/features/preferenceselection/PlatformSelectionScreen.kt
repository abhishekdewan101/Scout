package com.abhishek101.gamescout.features.preferenceselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewModel
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewState
import com.abhishek101.gamescout.design.new.system.SystemUiControlView
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

@Composable
fun PlatformSelectionScreen(
    viewModel: PreferenceSelectionViewModel = get()
) {
    val viewState by viewModel.viewState.collectAsState()

    SideEffect {
        viewModel.getPlatforms()
    }

    SystemUiControlView(
        statusBarColor = ScoutTheme.colors.primaryBackground,
        navigationBarColor = ScoutTheme.colors.primaryBackground,
        useDarkIcons = false
    ) {
        Box(
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
                        PreferenceSelectionViewState.Loading -> CircularProgressIndicator(color = ScoutTheme.colors.progressIndicatorOnPrimaryBackground)
                        else -> PlatformListView(viewState as PreferenceSelectionViewState.Result)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(75.dp))
                }
            }

            (viewState as? PreferenceSelectionViewState.Result)?.let {
                if (it.ownedPlatformCount == 0) {
                    DoneButtonView(modifier = Modifier.padding(bottom = 15.dp)) {
                        println("Going to genre selection")
                    }
                }
            }
        }
    }
}

@Composable
private fun PlatformListView(result: PreferenceSelectionViewState.Result) {
    val rows = result.platforms.chunked(2)
    for (row in rows) {
        Row {
            for (platform in row) {
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

    Button(
        onClick = onTap,
        colors = colors,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.width(200.dp)
    ) {
        Row {
            Text(
                text = "Done",
                color = ScoutTheme.colors.textOnPrimaryBackground,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
