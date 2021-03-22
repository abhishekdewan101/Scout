//
//  GenreSelection.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/20/21.
//

import SwiftUI
import core

struct GenreSelection: View {
    
    @ObservedObject var viewModel = GenreSelectionViewModel()
    
    var navigateForward: () -> Void
    
    init(navigateForward: @escaping () -> Void) {
        self.navigateForward = navigateForward
        self.viewModel.getGenres()
    }
    
    var body: some View {
        ZStack {
            VStack(alignment: .leading) {
                GenreHeading()
                GenreList(genreList:viewModel.genreList, onTapItem: {genre, isFavorite in
                    viewModel.setGenreAsFavorite(genre: genre, isFavorite: isFavorite)
                })
            }.padding()
            GenreSelectionDone(ownedGenres: viewModel.getFavoriteGenreCount(), navigateForward:navigateForward)
        }
    }
}

struct GenreHeading: View {
    var body: some View {
        Text("Favorite Genres")
            .font(.title)
            .fontWeight(.bold)
            .foregroundColor(Color("OnBackground"))
        Text("We will use these genres to tailor your search results")
            .font(.subheadline)
            .foregroundColor(Color("OnBackground"))
    }
}

struct GenreSelectionDone: View {
    var ownedGenres: Int
    var navigateForward: () -> Void
    
    var body: some View {
        if (ownedGenres > 0) {
            VStack{
                Button(action: {
                    navigateForward()
                }) {
                    Text("Done")
                        .padding(.horizontal,30)
                        .padding(.vertical,10)
                        .background(Color("Primary"))
                        .font(.title3)
                        .foregroundColor(Color("OnPrimary"))
                        .cornerRadius(10.0)
                }.padding()
            }
            .frame(minWidth: 0,
                   maxWidth: .infinity,
                   minHeight: 0,
                   maxHeight: .infinity,
                   alignment: .bottomTrailing)
        }
    }
}


struct GenreList: View {
    let layout = [GridItem(.flexible(minimum: 80)), GridItem(.flexible(minimum: 80))]
    
    var genreList: [Genre]
    var onTapItem: (Genre, Bool) -> Void
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: layout, spacing: 20) {
                ForEach(genreList, id: \.self) { item in
                    GenreListItem(item: item, onTap: onTapItem)
                }
            }
        }.padding(.top)
    }
}

struct GenreListItem: View {
    var item: Genre
    var onTap: (Genre, Bool) -> Void
    
    
    var body: some View {
        let borderColor = item.isFavorite == true ? Color("Primary") : Color("Background")
        
        VStack {
            Text(item.name)
                .font(.title3)
                .foregroundColor(Color("OnSurface")).lineLimit(1)
        }.onTapGesture {
            let isOwned = item.isFavorite == true
            onTap(item, !isOwned)
        }.padding()
        .frame(width: 150, height: 100, alignment: .center)
        .background(Color("Surface"))
        .cornerRadius(10.0)
        .padding()
        .border(borderColor, width: 5)
    }
}

struct GenreSelection_Previews: PreviewProvider {
    static var previews: some View {
        GenreList(genreList: [Genre(id: 0, slug: "Name", name: "Name", isFavorite: true)]) {_,_ in
            
        }
    }
}
