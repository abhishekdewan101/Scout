package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek101.gametracker.ui.components.carousel.Carousel
import org.koin.androidx.compose.get
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = get()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        Text(
            "Home",
            style = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 28.sp),
            modifier = Modifier.padding(top = 15.dp, start = 15.dp)
        )

        Box(modifier = Modifier.padding(top = 20.dp)) {
            Carousel(data = viewModel.carouselGameMap.toMap()) {
                Timber.d("User clicked on $it")
            }
        }
    }
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
