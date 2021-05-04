//
//  ExpandedList.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core

struct ExpandedList: View {

    var listData: GameListData

    init(listData: GameListData) {
        self.listData = listData
    }

    var body: some View {
        ZStack {
            Color.black.edgesIgnoringSafeArea(.all)
            ScrollView {
                let games = listData.games
                let imageIdList = games.filter { $0.cover != nil }.map { $0.cover!.qualifiedUrl }

                GamePosterGrid(imageIdList: imageIdList.map { $0 }) { index in
                    return games[index].slug
                }.navigationTitle("")

            }.padding(.top)
        }.navigationBarColor(.clear)
        .navigationBarItems(leading: Text(listData.title)
                                .font(.title)
                                .fontWeight(.bold)
                                .foregroundColor(Color.init("TitleColor")))
        .navigationBarTitleDisplayMode(.inline)
    }
}
