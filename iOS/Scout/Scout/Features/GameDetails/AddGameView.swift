//
//  AddGameView.swift
//  Scout
//
//  Created by Abhishek Dewan on 7/3/21.
//

import SwiftUI
import ScoutCommon

struct GameListModel<T: Hashable>: Hashable {
    var title: String
    var value: T
}

struct AddGameView: View {
    var gameName: String
    var coverUrl: String
    var platformList: [String]
    @Binding var libraryState: LibraryState?
    var onAddGame: (GameStatus, [String], String, Int) -> Void

    @State private var platformSelectionKeeper: [String] = []
    @State private var gameStatusSelectionKeeper: GameStatus?
    @State private var ratingSelectionKeeper: Double = 50.0
    @State private var gameNotesKeeper: String = "Jot down some notes about your experience"
    private var gameStatusList: [GameListModel<GameStatus>] = [
        GameListModel(title: "I want to buy it", value: GameStatus.want),
        GameListModel(title: "I own it", value: GameStatus.owned),
        GameListModel(title: "I'd like to play it next", value: GameStatus.queued),
        GameListModel(title: "I'm playing it now", value: GameStatus.playing),
        GameListModel(title: "I've completed it", value: GameStatus.completed),
        GameListModel(title: "I've abandoned the game", value: GameStatus.abandoned)
    ]
    let notesPlaceHolderText = "Jot down some notes about your experience"

    init(gameName: String,
         coverUrl: String,
         platformList: [String],
         libraryState: Binding<LibraryState?>,
         onAddGame: @escaping (GameStatus, [String], String, Int) -> Void) {
        self.gameName = gameName
        self.coverUrl = coverUrl
        self.platformList = platformList
        self._libraryState = libraryState
        self.onAddGame = onAddGame
    }

    var body: some View {
        GeometryReader { geo in
            VStack(alignment: .leading) {
                HStack {
                    AsyncImage(url: coverUrl,
                               width: 125,
                               height: 125,
                               contentMode: .fill,
                               cornerRadius: 15)
                    Text(gameName).font(.system(size: 16)).fontWeight(.bold).lineLimit(2)
                }.padding(.top)
                .padding(.horizontal)
                .frame(width: geo.size.width, alignment: .leading)

                Form {
                    Section(header: Text("Select the platforms you own?")
                                .font(.system(size: 16))
                                .fontWeight(.bold)) {
                        List {
                            ForEach(platformList, id: \.self) { platform in
                                Button {
                                    if platformSelectionKeeper.contains(platform) {
                                        platformSelectionKeeper.removeAll { $0 == platform }
                                    } else {
                                        platformSelectionKeeper.append(platform)
                                    }
                                } label: {
                                    HStack {
                                        let isSelected = platformSelectionKeeper.contains(platform)
                                        let imageName =  isSelected ? "checkmark.circle.fill" : "circle"
                                        Image(systemName: imageName)
                                            .renderingMode(.template)
                                            .foregroundColor(Color.white)
                                        Text(platform)
                                    }
                                }.buttonStyle(PlainButtonStyle())
                            }
                        }
                    }

                    Section(header: Text("Select a game status")
                                .font(.system(size: 16))
                                .fontWeight(.bold)) {
                        List {
                            ForEach(gameStatusList, id: \.self) { gameStatus in
                                Button {
                                    gameStatusSelectionKeeper = gameStatus.value
                                } label: {
                                    HStack {
                                        let isSelected = gameStatusSelectionKeeper == gameStatus.value
                                        let imageName =  isSelected ? "checkmark.circle.fill" : "circle"
                                        Image(systemName: imageName)
                                            .renderingMode(.template)
                                            .foregroundColor(Color.white)
                                        Text(gameStatus.title)
                                    }
                                }.buttonStyle(PlainButtonStyle())
                            }
                        }
                    }

                    if gameStatusSelectionKeeper == GameStatus.completed ||
                        gameStatusSelectionKeeper == GameStatus.abandoned {

                        let notesTextColor = gameNotesKeeper == notesPlaceHolderText ? Color.gray : Color.white

                        Section(header: Text("Rate your experience")
                                    .font(.system(size: 16))
                                    .fontWeight(.bold)) {
                            VStack {
                                HStack {
                                    Text("Rating:")
                                        .font(.body)
                                        .fontWeight(.semibold)
                                        .foregroundColor(Color.gray)
                                    Text(String(Int(ratingSelectionKeeper)))
                                        .foregroundColor(Color.white)
                                }
                                Slider(value: $ratingSelectionKeeper,
                                       in: 0...100,
                                       step: 1)
                                    .accentColor(Color.white)
                                Text("Notes")
                                    .font(.body)
                                    .fontWeight(.semibold)
                                    .foregroundColor(Color.gray)
                                TextEditor(text: $gameNotesKeeper)
                                    .padding()
                                    .background(Color.gray.opacity(0.5))
                                    .foregroundColor(notesTextColor)
                                    .font(.body)
                                    .frame(minHeight: 100, maxHeight: .infinity)
                                    .cornerRadius(15)
                                    .onTapGesture {
                                        if gameNotesKeeper == self.notesPlaceHolderText {
                                            gameNotesKeeper = ""
                                        }
                                    }
                                    .padding(.bottom)
                            }
                        }
                    }

                    if platformSelectionKeeper.count > 0 && gameStatusSelectionKeeper != nil {
                        Button {
                            if let status = gameStatusSelectionKeeper {
                                onAddGame(status, platformSelectionKeeper, gameNotesKeeper, Int(ratingSelectionKeeper))
                            }
                        } label: {
                            Text("Done")
                                .font(.body)
                                .fontWeight(.bold)
                                .foregroundColor(Color.white)
                                .frame(maxWidth: .infinity)
                        }.buttonStyle(BorderlessButtonStyle()) // Fixing this issue https://www.hackingwithswift.com/forums/swiftui/buttons-in-a-form-section/6175
                    }
                }
            }.frame(alignment: .topLeading)
            .onTapGesture {
                hideKeyboard()
            }
        }.onAppear {
            if let state = libraryState {
                platformSelectionKeeper = state.platformList
                gameStatusSelectionKeeper = state.gameStatus
                if let rating = state.gameRating, let notes = state.gameNotes {
                    ratingSelectionKeeper = rating.doubleValue
                    gameNotesKeeper = notes
                }
            }
        }
    }
}
