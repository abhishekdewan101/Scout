//
//  GameDetailScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/28/21.
//

import SwiftUI
import ScoutCommon

struct GameDetailScreen: View {
    var slug: String

    @State private var viewState: GameDetailViewState = GameDetailViewState.EmptyViewState()

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: GameDetailViewModel.self) as! GameDetailViewModel

    var body: some View {
        if viewState is GameDetailViewState.EmptyViewState {
            VStack {
                ProgressView()
                    .scaleEffect(x: 2, y: 2, anchor: .center)
                    .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                            viewModel.constructGameDetails(slug: slug) {
                                viewState = $0
                            }
                        }
                    }
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
        } else {
            // swiftlint:disable:next force_cast
            let result = viewState as! GameDetailViewState.NonEmptyViewState
            ScrollView(.vertical, showsIndicators: false) {
                VStack {
                    HStack {
                        AsyncImage(url: result.coverUrl,
                                   width: 175,
                                   height: 225,
                                   contentMode: .fill,
                                   cornerRadius: 5)
                        VStack(alignment: .leading, spacing: 10) {
                            Text(result.name).font(.title2).fontWeight(.bold).lineLimit(2)
                            if let developer = result.developer {
                                Text(developer.name).font(.body).foregroundColor(Color("White").opacity(0.7))
                            }
                            Text(result.releaseDate.dateString).font(.body).foregroundColor(Color("White").opacity(0.7))
                            if let rating = result.rating {
                                ZStack {
                                    Circle().fill(Color.green).frame(width: 45, height: 45)
                                    Text("\(rating)").font(.body).fontWeight(.bold)
                                }
                            }
                        }
                        .frame(alignment: .topLeading)
                        .padding(.leading)
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.top)
                .padding(.horizontal)
            }.navigationBarTitle(result.name)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                Button {
                    print("save game")
                } label: {
                    Image(systemName: "heart")
                }
            }
        }
    }
}

struct GameDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        GameDetailScreen(slug: "Something")
    }
}
