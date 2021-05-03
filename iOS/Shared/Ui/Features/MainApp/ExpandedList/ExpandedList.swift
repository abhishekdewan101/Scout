//
//  ExpandedList.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core

struct ExpandedList: View {

    var listData: GameListData?

    init(listData: GameListData?) {
        self.listData = listData
    }

    var body: some View {
        ZStack {
            Color.black
            if listData != nil {
                ScrollView {
                    VStack(alignment: .leading) {
                        HStack(alignment: .center) {
                            Image(systemName: "chevron.left")
                                .frame(width: 24, height: 24)
                                .foregroundColor(Color.white)
                                .padding(.top, 20)
                                .onTapGesture {

                                }
                            Text(listData!.title)
                                .font(.system(size: 36))
                                .fontWeight(.bold)
                                .foregroundColor(Color.init("TitleColor"))
                                .padding(.leading, 10)
                                .padding(.top, 20)
                        }.padding(.leading)
                    }.frame(
                        minWidth: 0,
                        maxWidth: .infinity,
                        alignment: .topLeading
                    )

                    if listData != nil {
                        let games = listData!.games
                        let imageIdList = games.filter {$0.cover != nil}.map {$0.cover!.qualifiedUrl}

                        GamePosterGrid(imageIdList: imageIdList.map {$0}) { _ in

                        }
                    }

                }.padding(.top)
            }
        }.edgesIgnoringSafeArea(.all)
    }
}
