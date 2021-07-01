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
                    GameDetailHeaderView(result: result)
                    if let summary = result.summary {
                        GameDetailSummary(summary: summary)
                    }
                }.frame(maxWidth: .infinity, alignment: .leading)
            }.navigationBarTitle(result.name, displayMode: .inline)
            .toolbar {
                // Fix https://stackoverflow.com/questions/64405106/toolbar-is-deleting-my-back-button-in-the-navigationview/64994154#64994154
                ToolbarItem(placement: .navigationBarLeading) {Text("")}
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        print("Save Game")
                    } label: {
                        Image(systemName: "heart").font(.body)
                    }
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

struct GameDetailSummary: View {
    var summary: String

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("About")
                .font(.title)
                .fontWeight(.bold)
                .foregroundColor(Color("White").opacity(0.5))
            ExpandableTextField(text: summary)
        }
        .frame(maxWidth: .infinity)
        .padding(.top)
    }
}

struct GameDetailHeaderView: View {
    var result: GameDetailViewState.NonEmptyViewState

    var body: some View {
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
                    RatingView(rating: rating.intValue)
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.leading)
        }
    }
}

struct RatingView: View {
    var rating: Int

    func getRatingColor(rating: Int) -> Color {
        switch rating {
        case 75...100: return Color.green
        case 55...74: return Color.yellow
        case 0...74: return Color.red
        default: return Color.blue
        }
    }

    var body: some View {
        ZStack {
            Circle()
                .fill(getRatingColor(rating: rating))
                .frame(width: 45, height: 45)
            Text(String(rating)).font(.body).fontWeight(.bold)
        }
    }
}
