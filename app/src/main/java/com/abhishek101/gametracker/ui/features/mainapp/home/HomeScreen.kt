package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Text("Home Screen")
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
