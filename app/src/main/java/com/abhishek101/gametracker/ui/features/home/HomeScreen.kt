package com.abhishek101.gametracker.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abhishek101.gametracker.ui.components.bottomnavigation.BottomNavigationPager
import com.abhishek101.gametracker.ui.components.bottomnavigation.BottomNavigationTabData
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get()) {
    val bottomTabs = listOf(
        BottomNavigationTabData(Icons.Outlined.Home, "Home", "Home"),
        BottomNavigationTabData(Icons.Outlined.Search, "Search", "Search"),
        BottomNavigationTabData(Icons.Outlined.Person, "Profile", "Profile"),
    )
    BottomNavigationPager(
        bottomTabs = bottomTabs,
        pagerContent = { CreateHomeScreenContent(index = it) })
}

@Composable
fun CreateHomeScreenContent(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (index) {
                    0 -> Color.Red.copy(alpha = 0.5f)
                    1 -> Color.Green.copy(alpha = 0.5f)
                    2 -> Color.Blue.copy(alpha = 0.5f)
                    else -> Color.Black
                }
            )
    )
}

// @Composable
// fun HomeScreenContent(
//     bottomSelectedTab: BottomNavigationState,
//     updateBottomSelected: (BottomNavigationState) -> Unit
// ) {
//     val scaffoldState = rememberScaffoldState()
//     Scaffold(
//         scaffoldState = scaffoldState,
//         content = {
//             ScaffoldContent(bottomSelectedTab)
//         },
//         bottomBar = {
//             BottomBarContent(
//                 bottomSelectedTab = bottomSelectedTab,
//                 updateBottomSelected = updateBottomSelected
//             )
//         }
//     )
// }
//
// @Composable
// private fun BottomBarContent(
//     bottomSelectedTab: BottomNavigationState,
//     updateBottomSelected: (BottomNavigationState) -> Unit
// ) {
//     BottomAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
//         val selectedColor = MaterialTheme.colors.primary
//         val unSelectedColor = MaterialTheme.colors.onBackground
//
//         val selectedModifier =
//             Modifier
//                 .weight(1f)
//                 .padding(horizontal = 35.dp, vertical = 10.dp)
//                 .clip(RoundedCornerShape(15.dp))
//                 .background(MaterialTheme.colors.onBackground)
//
//         val unselectedModifier = Modifier.weight(1f)
//
//         IconButton(
//             modifier = if (bottomSelectedTab == GAME_LIST) {
//                 selectedModifier
//             } else {
//                 unselectedModifier
//             },
//             onClick = { updateBottomSelected(GAME_LIST) }
//         ) {
//             Icon(
//                 Icons.Filled.Games,
//                 "Game Lists",
//                 tint = if (bottomSelectedTab == GAME_LIST) {
//                     selectedColor
//                 } else {
//                     unSelectedColor
//                 }
//             )
//         }
//         IconButton(
//             modifier = if (bottomSelectedTab == SEARCH) {
//                 selectedModifier
//             } else {
//                 unselectedModifier
//             },
//             onClick = { updateBottomSelected(SEARCH) }
//         ) {
//             Icon(
//                 Icons.Filled.Search, "Search",
//                 tint = if (bottomSelectedTab == SEARCH) {
//                     selectedColor
//                 } else {
//                     unSelectedColor
//                 }
//             )
//         }
//     }
// }
//
// @Composable
// private fun ScaffoldContent(bottomNavigationState: BottomNavigationState) {
//     when (bottomNavigationState) {
//         SEARCH -> SearchContent()
//         GAME_LIST -> ListContent()
//     }
// }
//
// @Composable
// fun ListContent() {
//     val viewModel: HomeScreenViewModel = get()
//     Column {
//         ShowcaseGameList(bannerGameList = viewModel.bannerGameList.value)
//         TopRatedGameSection(topRatedGameList = viewModel.topRatedGameList.value)
//     }
// }
//
// @OptIn(ExperimentalFoundationApi::class)
// @Composable
// fun TopRatedGameSection(topRatedGameList: List<GamePosterRemoteEntity>) {
//     Column {
//         Text(
//             "Top Rated Games",
//             style = MaterialTheme.typography.h5,
//             color = MaterialTheme.colors.onBackground,
//             modifier = Modifier.padding(bottom = 10.dp)
//         )
//         LazyVerticalGrid(cells = GridCells.Fixed(2)) {
//             items(topRatedGameList.count()) {
//                 Column {
//                     CoilImage(
//                         data = "https://images.igdb.com/igdb/image/upload/t_720p/${topRatedGameList[it].cover.imageId}.jpeg",
//                         contentDescription = null,
//                         modifier = Modifier
//                             .size(150.dp, 200.dp)
//                             .clip(CircleShape.copy(CornerSize(20.dp)))
//                             .border(
//                                 width = 5.dp,
//                                 color = MaterialTheme.colors.onBackground,
//                                 shape = MaterialTheme.shapes.large
//                             ),
//                         error = {
//                             Text("Error")
//                         },
//                         loading = {
//                             CircularProgressIndicator()
//                         }
//                     )
//                     Text(topRatedGameList[it].name)
//                 }
//             }
//         }
//     }
// }
//
// @Composable
// fun ShowcaseGameList(bannerGameList: List<GamePosterRemoteEntity>) {
//     LazyRow(Modifier.padding(top = 15.dp)) {
//         items(bannerGameList.count()) {
//             Column(Modifier.padding(end = 10.dp, start = 10.dp)) {
//                 CoilImage(
//                     data = "https://images.igdb.com/igdb/image/upload/t_720p/${bannerGameList[it].screenShots[0].imageId}.jpeg",
//                     contentDescription = null,
//                     modifier = Modifier
//                         .size(400.dp, 300.dp)
//                         .border(
//                             5.dp,
//                             color = MaterialTheme.colors.onBackground,
//                             shape = RoundedCornerShape(10.dp)
//                         )
//                 )
//                 Text(
//                     bannerGameList[it].name,
//                     style = MaterialTheme.typography.h5,
//                     color = MaterialTheme.colors.onBackground
//                 )
//             }
//         }
//     }
// }
//
// @Composable
// fun SearchContent() {
//     Text(
//         "Search Screen",
//         style = MaterialTheme.typography.h6,
//         color = MaterialTheme.colors.onBackground
//     )
// }
//
// @Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL, device = Devices.PIXEL_4_XL)
// @Composable
// fun HomeScreenContentPreview() {
//     GameTrackerTheme {
//         HomeScreenContent(SEARCH) { _ -> }
//     }
// }
