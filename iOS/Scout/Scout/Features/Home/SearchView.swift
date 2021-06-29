//
//  SearchView.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/27/21.
//

import SwiftUI
import ScoutCommon

struct SearchView: View {

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: SearchViewModel.self) as! SearchViewModel

    @State private var viewState: SearchViewState = SearchViewState.Initial()

    var body: some View {
        GeometryReader { geometry in
            VStack {
                SearchBar { searchTerm in
                    viewModel.searchForGame(searchTerm: searchTerm) { state in
                        viewState = state
                    }
                } resetSearch: {
                    viewModel.resetSearchState()
                }.padding(.horizontal)
                ScrollView(.vertical, showsIndicators: false) {
                    SearchResults(viewState: viewState, screenSize: geometry.size)
                }
            }

        }
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
    }
}

struct SearchBar: View {
    @State private var searchTerm: String = ""
    @State private var isEditing: Bool = false

    var executeSearch: (String) -> Void
    var resetSearch: () -> Void

    var body: some View {
        HStack {
            HStack {
                Image(systemName: "magnifyingglass")
                    .foregroundColor(Color.white)
                    .padding(.vertical)
                    .padding(.leading)
                TextField("Enter game name",
                          text: $searchTerm,
                          onCommit: {
                            executeSearch(searchTerm)
                          }
                ).onTapGesture {
                    isEditing = true
                }
                if searchTerm.count > 0 {
                    Button {
                        searchTerm = ""
                    } label: {
                        Image(systemName: "multiply")
                            .foregroundColor(Color.white)
                    }.padding(.horizontal)
                    .padding(.vertical)
                }
            }
            .navigationBarHidden(isEditing)
            .background(Color("BrandBackground"))
            .cornerRadius(10)

            if isEditing {
                Button {
                    searchTerm = ""
                    isEditing = false
                    resetSearch()
                } label: {
                    Text("Cancel")
                        .font(.body)
                        .fontWeight(.bold)
                }.padding(.horizontal)
            }
        }
    }
}

struct SearchResults: View {
    var viewState: SearchViewState
    var screenSize: CGSize

    var body: some View {
        VStack {
            if viewState is SearchViewState.Loading {
                VStack {
                    ProgressView()
                        .scaleEffect(x: 2, y: 2, anchor: .center)
                        .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
                }.frame(width: screenSize.width)      // Make the scroll view full-width
                .frame(minHeight: screenSize.height)
            } else if viewState is SearchViewState.SearchResults {
                // swiftlint:disable:next force_cast
                let resultState = viewState as! SearchViewState.SearchResults
                let idealWidth = (screenSize.width - 20 ) / 3
                let columns = [
                    GridItem(.fixed(idealWidth), spacing: 5),
                    GridItem(.fixed(idealWidth), spacing: 5),
                    GridItem(.fixed(idealWidth))
                ]
                if resultState.results.count > 0 {
                    LazyVGrid(columns: columns, spacing: 5) {
                        ForEach(resultState.results, id: \.self) { poster in
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
                } else {
                    EmptyResultsView(screenSize: screenSize)
                }
            }
        }
    }
}

struct EmptyResultsView: View {
    var screenSize: CGSize
    var body: some View {
        VStack {
            Image("corgi")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 64, height: 64, alignment: .center)
            Text("No search results found !").font(.title3)
        }.frame(width: screenSize.width)      // Make the scroll view full-width
        .frame(minHeight: screenSize.height)
    }
}
