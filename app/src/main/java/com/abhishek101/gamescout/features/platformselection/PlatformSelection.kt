package com.abhishek101.gamescout.features.platformselection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.abhishek101.core.db.Platform
import com.abhishek101.gamescout.components.SelectablePlatformGrid
import com.abhishek101.gamescout.design.LoadingIndicator
import com.abhishek101.gamescout.design.Padding
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.onboarding.LocalOnBoardingNavigator
import com.abhishek101.gamescout.features.onboarding.OnBoardingDestinations.GenreSelectionScreen
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSelection(viewModel: PlatformSelectionViewModel = get()) {

    val isLoading = viewModel.isLoading.value
    val platformList = viewModel.platforms.value
    val navController = LocalOnBoardingNavigator.current

    PlatformSelectionList(
        isLoading = isLoading,
        platformList = platformList,
        onPlatformSelected = viewModel::updateOwnedPlatform,
        navController = navController,
        ownedCount = viewModel.getOwnedPlatformCount()
    )
}

@Composable
fun PlatformSelectionList(
    isLoading: Boolean,
    platformList: List<Platform>,
    onPlatformSelected: (String, Boolean) -> Unit,
    navController: NavController,
    ownedCount: Int
) {
    SafeArea(padding = 15.dp, backgroundColor = Color.Black) {
        Box() {
            Column(modifier = Modifier.fillMaxSize()) {
                PlatformSelectionHeader()
                if (isLoading) {
                    LoadingIndicator(Color(203, 112, 209))
                } else {
                    Padding(top = 15.dp) {
                        SelectablePlatformGrid(
                            data = platformList,
                            columns = 2,
                            onSelected = onPlatformSelected
                        )
                    }
                }
            }
            RenderDoneButton(ownedCount, navController)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun RenderDoneButton(
    ownedCount: Int,
    navController: NavController
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
                        .background(Color(203, 112, 209))
                        .clickable {
                            navController.navigate(GenreSelectionScreen.toString())
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
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            "Platforms",
            style = MaterialTheme.typography.h4,
            color = Color(203, 112, 209),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Select the platforms your currently own",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground
        )
    }
}
