//
//  LibraryView.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/27/21.
//

import SwiftUI
import ScoutCommon

struct LibraryFilter: Hashable {
    var status: GameStatus?
    var title: String
}

struct LibraryView: View {

    @State private var libraryGames: [LibraryGame] = []

    @State private var currentFilter: LibraryFilter = LibraryFilter(status: nil, title: "All")

    private var libraryFilterMap = [
        LibraryFilter(status: nil, title: "All"),
        LibraryFilter(status: GameStatus.want, title: "Will Buy"),
        LibraryFilter(status: GameStatus.owned, title: "Not Played Yet"),
        LibraryFilter(status: GameStatus.playing, title: "Playing Now"),
        LibraryFilter(status: GameStatus.queued, title: "Might Play Next"),
        LibraryFilter(status: GameStatus.completed, title: "Completed Games"),
        LibraryFilter(status: GameStatus.abandoned, title: "Abandoned Games")
    ]

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: LibraryViewModel.self) as! LibraryViewModel

    var body: some View {
        GeometryReader { geometry in
            if libraryGames.count == 0 {
                EmptyResultsView(screenSize: geometry.size, message: "Oops you haven't added any games")
            } else {
                ScrollView(.vertical, showsIndicators: false) {
                    Menu {
                        Picker(selection: $currentFilter, label: Text("Sorting options")) {
                            ForEach(libraryFilterMap, id: \.self) { filter in
                                Text(filter.title).tag(filter)
                            }
                        }
                    } label: {
                        HStack {
                            Text("Showing: \(currentFilter.title)")
                                .padding(.all, 10)
                                .background(Color("LibraryFilterBackground"))
                                .foregroundColor(Color("LibraryFilterText"))
                                .cornerRadius(25)
                        }.frame(maxWidth: .infinity, alignment: .trailing)
                        .padding(.trailing, 5)
                    }
                    let idealWidth = (geometry.size.width - 20 ) / 3
                    let columns = [
                        GridItem(.fixed(idealWidth), spacing: 5),
                        GridItem(.fixed(idealWidth), spacing: 5),
                        GridItem(.fixed(idealWidth))
                    ]
                    LazyVGrid(columns: columns, spacing: 5) {
                        let filteredGames = libraryGames.filter({ game in
                            if currentFilter.status !=  nil {
                                return game.gameStatus == currentFilter.status
                            } else {
                                return true
                            }
                        })
                        ForEach(filteredGames, id: \.self) { game in
                            NavigationLink(destination: GameDetailScreen(slug: game.slug)) {
                                AsyncImage(url: game.coverUrl,
                                           width: Int(idealWidth),
                                           height: 200,
                                           contentMode: .fill,
                                           cornerRadius: 5)
                            }
                        }
                    }.padding(.top)
                }

            }
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        .onAppear {
            viewModel.getLibraryGames { libraryGames in
                self.libraryGames = libraryGames
            }
        }
    }
}

struct LibraryView_Previews: PreviewProvider {
    static var previews: some View {
        LibraryView()
    }
}
