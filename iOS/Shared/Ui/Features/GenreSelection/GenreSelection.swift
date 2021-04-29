//
//  GenreSelection.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI
import core

struct GenreSelection: View {
    
    var navigateForward: () -> Void
    @ObservedObject var viewModel = GenreSelectionViewModel()
    
    init(navigateForward: @escaping () -> Void) {
        self.navigateForward = navigateForward
    }
    
    var body: some View {
        FullScreenZStack {
            if (viewModel.isLoading) {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: Color.orange))
                    .scaleEffect(x: 2, y: 2, anchor: .center)
            } else {
                MainContent
                if (viewModel.getFavoriteGenreCount() > 0) {
                    VStack{
                        Spacer()
                        Button(action: {navigateForward()}) {
                            Text("Done")
                                .foregroundColor(.white)
                                .fontWeight(.bold)
                                .frame(minWidth: 0, maxWidth: .infinity, maxHeight: 50)
                                .background(Color.orange)
                        }.cornerRadius(10.0)
                        .padding([.leading, .trailing], 30)
                    }
                }
            }
        }
    }
    
    private var MainContent: some View {
        FullScreenVStack(alignment: HorizontalAlignment.leading) {
            Text("Platforms").font(.system(size: 34)).foregroundColor(.orange).fontWeight(.bold).padding(.leading)
            Text("Select the platforms your currently own").font(.body).foregroundColor(.white).padding(.leading)
            ScrollView {
                LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], alignment: .leading, spacing: 10, pinnedViews: [], content: {
                    ForEach(0 ..< viewModel.genreList.count) { index in
                        let genre = viewModel.genreList[index] as Genre
                        let isFavorite = genre.isFavorite == true
                        CircularSelectableImage(isSelected: isFavorite, isSelectedColor: .orange, imageId: genre.slug, isImageRemote: false, title: genre.name) {
                            viewModel.setGenreAsFavorite(genre: genre, isFavorite: !isFavorite)
                        }
                    }
                }).padding(.all)
            }
        }
    }
}

struct GenreSelection_Previews: PreviewProvider {
    static var previews: some View {
        GenreSelection() {
        
        }
    }
}
