//
//  GameListView.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/27/21.
//

import SwiftUI
import ScoutCommon

struct GameListView: View {
    @State private var viewState: GameListViewState = GameListViewState.Loading()
    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: GameListViewModel.self) as! GameListViewModel

    var body: some View {
        if viewState is GameListViewState.Loading {
            ProgressView()
                .scaleEffect(x: 2, y: 2, anchor: .center)
                .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
                .onAppear {
                    viewModel.getGameLists { state in
                        viewState = state
                    }
                }
        } else {
            // swiftlint:disable:next force_cast
            let result = viewState as! GameListViewState.NonEmptyViewState
            GeometryReader { geometery in
                ScrollView(.vertical, showsIndicators: false) {
                    ShowcaseListView(result: result)
                        .padding(.bottom)
                    ForEach(result.otherLists.indices, id: \.self) { index in
                        let otherList = result.otherLists[index]
                        if index % 2 == 0 {
                            GamePosterGridView(posterList: otherList, screenSize: geometery.size)
                        } else {
                            GamePosterHorizontalListView(posterList: otherList)
                        }
                    }

                }
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        }
    }
}

struct GameListView_Previews: PreviewProvider {
    static var previews: some View {
        GameListView()
    }
}

struct ShowcaseListView: View {
    let result: GameListViewState.NonEmptyViewState
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack {
                ForEach(result.headerList.games, id: \.self) { game in
                    Button {
                        print("Showing game details for \(game.slug)")
                    } label: {
                        // swiftlint:disable:next force_unwrapping
                        AsyncImage(url: game.screenShots![0].qualifiedUrl,
                                   width: 400,
                                   height: 200,
                                   contentMode: .fill,
                                   cornerRadius: 20)
                            .onTapGesture {
                                print("Showing game details for \(game.slug)")
                            }
                    }
                }
            }.padding(.top)
        }
    }
}

struct GamePosterGridView: View {
    var posterList: GameListData
    var screenSize: CGSize

    var body: some View {
        VStack {
            let list = posterList
            Header(list: posterList)
            let idealWidth = (screenSize.width - 20 ) / 3
            let columns = [
                GridItem(.fixed(idealWidth), spacing: 5),
                GridItem(.fixed(idealWidth), spacing: 5),
                GridItem(.fixed(idealWidth))
            ]
            LazyVGrid(columns: columns, spacing: 5) {
                let posters = list.games[0...8] // take first 9
                ForEach(posters, id: \.self) { poster in
                    Button {
                        print("Showing game details for \(poster.slug)")
                    } label: {
                        // swiftlint:disable:next force_unwrapping
                        AsyncImage(url: poster.cover!.qualifiedUrl,
                                   width: Int(idealWidth),
                                   height: 200,
                                   contentMode: .fill,
                                   cornerRadius: 5)
                    }
                }
            }
        }
    }
}

struct Header: View {
    var list: GameListData
    var body: some View {
        HStack(alignment: .center) {
            Text(list.title)
                .font(.title)
                .fontWeight(.bold)
            Spacer()
            Button {
                print("View More for \(list.listType)")
            } label: {
                Image(systemName: "ellipsis")
                    .rotationEffect(.degrees(90))
                    .font(.title3)
            }.padding(.trailing)
        }
    }
}

struct GamePosterHorizontalListView: View {
    var posterList: GameListData
    var body: some View {
        let reducedGameList = posterList.games[0...8]
        VStack {
            Header(list: posterList)
            ScrollView(.horizontal, showsIndicators: false) {
                HStack {
                    ForEach(reducedGameList, id: \.self) { game in
                        Button {
                            print("Showing game details for \(game.slug)")
                        } label: {
                            // swiftlint:disable:next force_unwrapping
                            AsyncImage(url: game.cover!.qualifiedUrl,
                                       width: 150,
                                       height: 250,
                                       contentMode: .fill,
                                       cornerRadius: 20)
                        }
                    }
                }
            }
        }
    }
}
