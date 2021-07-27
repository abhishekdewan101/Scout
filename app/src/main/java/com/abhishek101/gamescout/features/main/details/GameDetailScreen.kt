package com.abhishek101.gamescout.features.main.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewState
import com.abhishek101.gamescout.design.new.image.RemoteImage
import com.abhishek101.gamescout.design.new.system.ProgressIndicator
import com.abhishek101.gamescout.features.main.AppScreens
import com.abhishek101.gamescout.theme.ScoutTheme
import org.koin.androidx.compose.get

private val COVER_HEIGHT = 350.dp

@ExperimentalMaterialApi
@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = get(),
    data: String,
    navigateToScreen: (AppScreens, Any) -> Unit,
    updateModalState: (ModalBottomSheetValue) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.viewState.collectAsState()
    val scrollState = rememberScrollState()

    SideEffect {
        viewModel.constructGameDetails(slug = data)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                when (viewState) {
                    GameDetailViewState.EmptyViewState -> ProgressIndicator(indicatorColor = ScoutTheme.colors.progressIndicatorOnSecondaryBackground)
                    else -> {
                        GameDetails(
                            game = viewState as GameDetailViewState.NonEmptyViewState,
                            scrollState = scrollState,
                            navigateToScreen = navigateToScreen
                        ) { shouldDelete ->
                            if (shouldDelete) {
                                viewModel.removeGame()
                            } else {
                                updateModalState(ModalBottomSheetValue.Expanded)
                            }
                        }
                        FadingToolBar(game = viewState as GameDetailViewState.NonEmptyViewState, scrollState = scrollState) {
                            updateModalState(ModalBottomSheetValue.Expanded)
                        }
                    }
                }
            }
        },
        backgroundColor = ScoutTheme.colors.secondaryBackground
    )
}

// region Content
@Composable
private fun GameDetails(
    game: GameDetailViewState.NonEmptyViewState,
    scrollState: ScrollState,
    navigateToScreen: (AppScreens, Any) -> Unit,
    changeGameState: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        HeaderImage(mediaList = game.mediaList)
        Spacer(modifier = Modifier.height(10.dp))
        PrimaryDetails(game = game, changeGameState = changeGameState)
        ColumnDivider()
        MetadataDetails(game = game)
        ColumnDivider()
        ImageGallery(game = game) {
            navigateToScreen(AppScreens.IMAGE_VIEWER, it)
        }
        ColumnDivider()
    }
}

@Composable
private fun ImageGallery(game: GameDetailViewState.NonEmptyViewState, showImageViewer: (String) -> Unit) {
    val images = game.mediaList
    BoxWithConstraints {
        LazyRow(modifier = Modifier.padding(start = 10.dp)) {
            items(images) {
                RemoteImage(
                    request = it,
                    contentDescription = "game screenshot",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(maxWidth - 20.dp)
                        .height(height = 200.dp)
                        .padding(end = 10.dp)
                        .padding(top = 10.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .clickable { showImageViewer(game.slug) }
                )
            }
        }
    }
}

@Composable
private fun MetadataDetails(game: GameDetailViewState.NonEmptyViewState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Text(
                text = "Release Date",
                color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Text(
                text = game.releaseDate.dateString,
                color = ScoutTheme.colors.textOnSecondaryBackground,
                style = MaterialTheme.typography.body1,
            )
        }
        RowDivider()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Text(
                text = "Avg Rating",
                color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            if (game.rating != null) {
                GameRating(rating = game.rating!!)
            } else {
                Text(
                    text = "Not Rated",
                    color = ScoutTheme.colors.textOnSecondaryBackground,
                    style = MaterialTheme.typography.body1
                )
            }
        }
        RowDivider()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Text(
                text = "Genres",
                color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Text(
                text = game.genres.joinToString(separator = "\n"),
                color = ScoutTheme.colors.textOnSecondaryBackground,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
private fun GameRating(rating: Int) {
    val backgroundColor = when {
        rating >= 75 -> ScoutTheme.colors.topRatingColor
        rating in 55..74 -> ScoutTheme.colors.mediumRatingColor
        else -> ScoutTheme.colors.lowRatingColor
    }
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(50)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                rating.toString(),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun RowDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(ScoutTheme.colors.dividerColor.copy(alpha = 0.12f))
    )
}

@Composable
private fun ColumnDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        color = ScoutTheme.colors.dividerColor.copy(alpha = 0.12f), // Get this value from the Divider Composables internal value
    )
}

@Composable
private fun PrimaryDetails(game: GameDetailViewState.NonEmptyViewState, changeGameState: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        RemoteImage(
            request = game.coverUrl,
            contentDescription = game.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(125.dp)
                .clip(MaterialTheme.shapes.large)
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(start = 10.dp)
                .height(125.dp)
        ) {
            Text(
                text = game.name,
                color = ScoutTheme.colors.textOnSecondaryBackground,
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            game.developer?.let {
                Text(
                    text = "by ${it.name}",
                    color = ScoutTheme.colors.secondaryTextOnSecondaryBackground,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Button(
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (game.inLibrary) ScoutTheme.colors.deleteButtonColor else ScoutTheme.colors.addButtonColor,
                    contentColor = ScoutTheme.colors.textOnButtonColor
                ),
                onClick = {
                    changeGameState(game.inLibrary)
                }
            ) {
                if (game.inLibrary) Text("Remove Game") else Text("Save Game")
            }
        }
    }
}

@Composable
private fun HeaderImage(mediaList: List<String>) {
    if (mediaList.isNotEmpty()) {
        RemoteImage(
            request = mediaList.random(),
            contentDescription = "game cover image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(COVER_HEIGHT)
        )
    }
}

// endregion Content

// region TopBar

@Composable
private fun FadingToolBar(game: GameDetailViewState.NonEmptyViewState, scrollState: ScrollState, showAddGameModal: () -> Unit) {
    val alphaOffset = 0f.coerceAtLeast(scrollState.value / COVER_HEIGHT.value).coerceIn(0f..1f)
    TopAppBar(
        backgroundColor = ScoutTheme.colors.topBarBackground.copy(alpha = alphaOffset),
        elevation = 0.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIos,
                contentDescription = "Back",
                tint = ScoutTheme.colors.topBarTextColor,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable {
                    }
            )

            Text(
                text = game.name,
                style = MaterialTheme.typography.h6,
                color = ScoutTheme.colors.topBarTextColor.copy(alpha = alphaOffset),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(6f)
            )
            Actions(inLibrary = game.inLibrary, showAddGameModal = showAddGameModal)
        }
    }
}

@Composable
private fun RowScope.Actions(inLibrary: Boolean, showAddGameModal: () -> Unit) {
    if (inLibrary) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = "Edit",
            tint = ScoutTheme.colors.topBarTextColor,
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f)
                .clickable {
                }
        )
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete",
            tint = ScoutTheme.colors.topBarTextColor,
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f)
                .clickable {
                }
        )
    } else {
        Icon(
            imageVector = Icons.Outlined.Save,
            contentDescription = "Save",
            tint = ScoutTheme.colors.topBarTextColor,
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f)
                .clickable {
                    showAddGameModal()
                }
        )
    }
}

// endregion TopBar
