//
//  HomeScreen.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI
import core
import URLImage

struct HomeScreen: View {

    @ObservedObject var viewModel = HomeScreenViewModel()

    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Text("Home")
                    .font(.system(size: 36))
                    .fontWeight(.bold)
                    .foregroundColor(Color.init("TitleColor"))
                    .padding(.leading, 20)
                    .padding(.top, 20)

                if viewModel.showcaseListData.games.count > 0 {
                    let games = viewModel.showcaseListData.games
                    let imageIdList = games.filter {$0.screenShots != nil}.map {$0.screenShots![0].qualifiedUrl}
                    HorizontalImageList(imageIdList: imageIdList.prefix(9).map {$0},
                            imageWidth: 400, imageHeight: 200) { index in
                        print("Game Selected \(games[index])")
                    }
                }

                if viewModel.comingSoonData.games.count > 0 {
                    let listData = viewModel.comingSoonData
                    let games = listData.games
                    let imageIdList = games.filter {$0.cover != nil}.map {$0.cover!.qualifiedUrl}

                    TitledContainer(title: listData.title, onViewMoreClicked: {
                        print("View More Clicked")
                    }) {
                        GamePosterGrid(imageIdList: imageIdList.prefix(9).map {$0}) { index in
                            print("Game Selected \(games[index])")
                        }
                    }
                    .padding(.top)
                }

                if viewModel.recentGameData.games.count > 0 {
                    let listData = viewModel.recentGameData
                    let games = listData.games
                    let imageIdList = games.filter {$0.cover != nil}.map {$0.cover!.qualifiedUrl}

                    TitledContainer(title: listData.title, onViewMoreClicked: {
                        print("View More Clicked")
                    }) {
                        HorizontalImageList(imageIdList: imageIdList.prefix(9).map {$0},
                                imageWidth: 150, imageHeight: 200) { index in
                            print("Game Selected \(games[index])")
                        }
                    }
                    .padding(.top)
                }

                if viewModel.hypedGameData.games.count > 0 {
                    let listData = viewModel.hypedGameData
                    let games = listData.games
                    let imageIdList = games.filter {$0.cover != nil}.map {$0.cover!.qualifiedUrl}

                    TitledContainer(title: listData.title, onViewMoreClicked: { print("View More Clicked") }) {
                        GamePosterGrid(imageIdList: imageIdList.prefix(9).map {$0}) { index in
                            print("Game Selected \(games[index])")
                        }
                    }
                    .padding(.top)
                }

                if viewModel.topRatedGameData.games.count > 0 {
                    let listData = viewModel.topRatedGameData
                    let games = listData.games
                    let imageIdList = games.filter {$0.cover != nil}.map {$0.cover!.qualifiedUrl}

                    TitledContainer(title: listData.title, onViewMoreClicked: { print("View More Clicked") }) {
                        HorizontalImageList(imageIdList: imageIdList.prefix(9).map {$0},
                                imageWidth: 150, imageHeight: 200) { index in
                            print("Game Selected \(games[index])")
                        }
                    }
                    .padding(.top)
                    .padding(.bottom)
                }
            }
            .frame(
                minWidth: 0,
                maxWidth: .infinity,
                alignment: .topLeading
            )
        }
        .padding(.top)
        .navigationBarHidden(true)
        .background(Color.black).edgesIgnoringSafeArea(.top)
    }
}

struct HomeScreenPreview: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
