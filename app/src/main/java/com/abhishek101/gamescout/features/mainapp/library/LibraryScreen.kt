package com.abhishek101.gamescout.features.mainapp.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.R
import com.abhishek101.gamescout.design.DropdownChip
import com.abhishek101.gamescout.design.ImageGrid
import com.abhishek101.gamescout.design.SafeArea
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.ABANDONED
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.ALL
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.COMPLETED
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.PLAYING
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.QUEUE
import com.abhishek101.gamescout.features.mainapp.library.LibraryFilters.WANTED
import com.abhishek101.gamescout.features.mainapp.navigator.MainAppDestinations
import com.google.accompanist.coil.CoilImage
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel = get(), navigate: (String) -> Unit) {

    val coverUrls = viewModel.libraryGames.map { it.coverUrl }

    SafeArea(padding = 15.dp, bottomOverride = 56.dp) {
        BoxWithConstraints {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .background(MaterialTheme.colors.background),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Library",
                            style = MaterialTheme.typography.h4.copy(
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        DropdownChip(
                            data = mapOf(
                                ALL to "All",
                                WANTED to "Wanted",
                                QUEUE to "Queued",
                                PLAYING to "Playing",
                                COMPLETED to "Completed",
                                ABANDONED to "Abandoned"
                            ),
                            chipBackgroundColor = MaterialTheme.colors.onBackground,
                            cornerShape = RoundedCornerShape(50),
                            initialValue = ALL,
                            textColor = MaterialTheme.colors.background,
                            iconColor = MaterialTheme.colors.background,
                            dropDownIconLess = Icons.Outlined.ExpandLess,
                            dropDownIconMore = Icons.Outlined.ExpandMore,
                            dropDownBackgroundColor = MaterialTheme.colors.onBackground,
                            dropDownTextColor = MaterialTheme.colors.background
                        ) {
                            viewModel.updateGamesForFilter(it)
                        }
                    }
                }
                if (coverUrls.isNotEmpty()) {
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        ImageGrid(
                            data = coverUrls,
                            columns = 3,
                            imageWidth = maxWidth / 3,
                            imageHeight = 175.dp
                        ) {
                            navigate(
                                "${MainAppDestinations.GameDetail}/${
                                viewModel.libraryGames[it].slug
                                }"
                            )
                        }
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CoilImage(
                                data = R.drawable.corgi,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp, 64.dp)
                                    .padding(bottom = 10.dp)
                            )
                            Text(
                                "Looks like we couldn't find any games!",
                                color = MaterialTheme.colors.onBackground,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}
