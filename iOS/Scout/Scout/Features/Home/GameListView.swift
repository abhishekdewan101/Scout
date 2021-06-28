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
            ScrollView(.vertical, showsIndicators: false) {
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack {
                        ForEach(result.headerList.games, id: \.self) { game in
                            // swiftlint:disable:next force_unwrapping
                            AsyncImage(url: game.screenShots![0].qualifiedUrl,
                                       width: 400,
                                       height: 200,
                                       contentMode: .fill,
                                       cornerRadius: 20)
                        }
                    }.padding(.top)
                }
            }
        }
    }
}

struct GameListView_Previews: PreviewProvider {
    static var previews: some View {
        GameListView()
    }
}
