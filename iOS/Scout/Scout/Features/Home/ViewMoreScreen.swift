//
//  ViewMoreGamesScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/28/21.
//

import SwiftUI
import ScoutCommon

struct ViewMoreScreen: View {
    var listData: GameListData

    var body: some View {
        GeometryReader { geometry in
            ScrollView(.vertical, showsIndicators: false) {
                let idealWidth = (geometry.size.width - 20 ) / 3
                let columns = [
                    GridItem(.fixed(idealWidth), spacing: 5),
                    GridItem(.fixed(idealWidth), spacing: 5),
                    GridItem(.fixed(idealWidth))
                ]
                LazyVGrid(columns: columns, spacing: 5) {
                    ForEach(listData.games, id: \.self) { poster in
                        Button {
                            print("Showing game details for \(poster.slug)")
                        } label: {
                            NavigationLink(destination: GameDetailScreen(slug: poster.slug)) {
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
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        .navigationBarTitle(listData.title)
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct ViewMoreGamesScreen_Previews: PreviewProvider {
    static var previews: some View {
        ViewMoreScreen(listData: GameListData(listType: ListType.comingSoon, title: "Coming Soon", games: []))
    }
}
