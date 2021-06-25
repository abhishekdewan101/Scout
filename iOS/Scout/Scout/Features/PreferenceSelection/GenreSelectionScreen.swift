//
//  GenreSelectionScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/22/21.
//

import SwiftUI
import ScoutCommon

struct GenreGridItem: View {
    var slug: String
    var name: String
    var imageName: String
    var isSelected: Bool = false
    var onClick: (String) -> Void

    var body: some View {
        VStack {
            ZStack {
                Circle()
                    .fill(Color("White"))
                    .frame(width: 175, height: 150, alignment: .center)
                    .padding()
                    .if(isSelected) {
                        $0.overlay(Circle().strokeBorder(Color("White"), lineWidth: 5))
                    }
                Image(imageName)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: CGFloat(85),
                           height: CGFloat(85),
                           alignment: .center
                    )
            }
            Text(name)
                .font(.title3)
                .fontWeight(.semibold)
                .lineLimit(2)
                .multilineTextAlignment(.center)
                .foregroundColor(Color("White"))
        }.onTapGesture {
            onClick(slug)
        }
    }
}

struct GenreSelectionScreen: View {
    @Binding var preferenceSelectionStage: PreferenceSelectionStage
    @State private var viewState: PreferenceSelectionViewState = PreferenceSelectionViewState.Loading()

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: PreferenceSelectionViewModel.self) as! PreferenceSelectionViewModel

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        if viewState is PreferenceSelectionViewState.Loading {
            ZStack {
                Color("Purple").edgesIgnoringSafeArea(.all)
                ProgressView()
                    .scaleEffect(x: 2, y: 2, anchor: .center)
                    .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
            }.onAppear {
                viewModel.getGenres { state in
                    viewState = state
                }
            }
        } else {
            // swiftlint:disable:next force_cast
            let result = viewState as! PreferenceSelectionViewState.Result
            ZStack(alignment: .bottom) {
                Color("Purple").edgesIgnoringSafeArea(.all)
                ScrollView {
                    VStack(alignment: .center, spacing: 5) {
                        Text("Genres")
                            .font(.largeTitle)
                            .foregroundColor(.white)
                            .fontWeight(.bold)
                        Text("What genres do you like to play?")
                            .font(.body)
                            .foregroundColor(.white)
                        LazyVGrid(columns: columns, spacing: 20) {
                            ForEach(result.genres.indices, id: \.self) { index in
                                let genre = result.genres[index]
                                GenreGridItem(slug: genre.slug,
                                              name: genre.name,
                                              imageName: genre.slug,
                                              // swiftlint:disable:next force_unwrapping
                                              isSelected: genre.isFavorite!.boolValue) { slug in
                                    // swiftlint:disable:next force_unwrapping
                                    viewModel.toggleGenre(genreSlug: genre.slug, isFavorite: !genre.isFavorite!.boolValue)
                                }
                            }
                        }.padding()
                        Spacer(minLength: 50)
                    }
                    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
                }
                if result.ownedGenreCount > 0 {
                    Button {
                        preferenceSelectionStage = .home
                    } label: {
                        HStack {
                            Image(systemName: "checkmark.circle.fill")
                                .font(.title2)
                            Text("Done")
                                .fontWeight(.semibold)
                                .font(.title2)
                        }
                        .padding()
                        .foregroundColor(.white)
                        .frame(maxWidth: 300)
                        .background(Color("Red"))
                        .cornerRadius(20)
                    }
                    .buttonStyle(PlainButtonStyle())
                } else {
                    EmptyView()
                }
            }
        }
    }
}

struct GenreSelectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        GenreSelectionScreen(preferenceSelectionStage: Binding.constant(.genreSelection))
    }
}
