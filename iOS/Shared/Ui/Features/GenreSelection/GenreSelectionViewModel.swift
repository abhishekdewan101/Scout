//
//  GenreSelectionViewModel.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import Foundation

import core

class GenreSelectionViewModel: ObservableObject {
    @Published var isLoading: Bool
    @Published var genreList: [Genre]

    let genreRepository = koin.get(objCProtocol: GenreRepository.self) as! GenreRepository

    init() {
        self.isLoading = true
        self.genreList = []
        getGenres()
    }

    func getGenres() {
        FlowExtensionsKt.asCommonFlow(genreRepository.getCachedGenres()).watch(block: { [self] genres in
            if genres?.count == 0 {
                self.genreRepository.updateCachedGenres(completionHandler: {_, _ in})
            } else {
                self.isLoading = false
                self.genreList = (genres as? [Genre])!
            }
        })
    }

    func setGenreAsFavorite(genre: Genre, isFavorite: Bool) {
        genreRepository.setGenreAsFavorite(slug: genre.slug, isFavorite: isFavorite)
    }

    func getFavoriteGenreCount() -> Int {
        return self.genreList.filter { $0.isFavorite == true}.count
    }
}
