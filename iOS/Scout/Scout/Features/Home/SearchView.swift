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
    @State private var recentSearchList: [String] = []
    @State private var isEditing: Bool = false
    @State private var searchTerm: String = ""
    @State private var hasRenderedOnce = false

    var body: some View {
        GeometryReader { geometry in
            VStack {
                SearchBar(searchTerm: $searchTerm, isEditing: $isEditing, hasRenderedOnce: $hasRenderedOnce) { searchTerm in
                    viewModel.searchForGame(searchTerm: searchTerm) { state in
                        viewState = state
                    }
                } resetSearch: {
                    viewModel.resetSearchState()
                }.padding(.horizontal)
                if isEditing {
                    ScrollView(.vertical, showsIndicators: false) {
                        SearchResults(viewState: viewState, screenSize: geometry.size)
                    }
                } else {
                    RecentSearchListView(recentSearchList: $recentSearchList) {
                        searchTerm = $0
                        isEditing = true
                        hasRenderedOnce = true
                        viewModel.searchForGame(searchTerm: $0) {
                            viewState = $0
                        }
                    }
                }
            }
        }.onAppear {
            viewModel.getRecentSearches {
                recentSearchList = $0
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
    @Binding var searchTerm: String
    @Binding var isEditing: Bool
    @Binding var hasRenderedOnce: Bool
    @State var showKeyboard: Bool = false

    var executeSearch: (String) -> Void
    var resetSearch: () -> Void

    var body: some View {
        HStack {
            HStack {
                Image(systemName: "magnifyingglass")
                    .foregroundColor(Color.white)
                    .padding(.vertical)
                    .padding(.leading)
                CustomTextField(text: $searchTerm,
                                inEditing: $isEditing,
                                isFirstResponder: $showKeyboard) {
                    showKeyboard = false
                    executeSearch(searchTerm)
                }.frame(height: 50)
                .onTapGesture {
                    showKeyboard = true
                    isEditing = true
                    hasRenderedOnce = true
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
            .background(Color("BrandBackground"))
            .cornerRadius(10)

            if isEditing {
                Button {
                    searchTerm = ""
                    isEditing = false
                    showKeyboard = false
                    resetSearch()
                } label: {
                    Text("Cancel")
                        .font(.body)
                        .fontWeight(.bold)
                }.padding(.horizontal)
            }
        }.navigationBarHidden(isEditing)
        .if(hasRenderedOnce) {
            $0.animation(.easeIn(duration: 0.25))
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
                    EmptyResultsView(screenSize: screenSize, message: "No search results found !")
                }
            }
        }
    }
}

struct EmptyResultsView: View {
    var screenSize: CGSize
    var message: String
    var body: some View {
        VStack {
            Image("corgi")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 64, height: 64, alignment: .center)
            Text(message).font(.title3)
        }.frame(width: screenSize.width)      // Make the scroll view full-width
        .frame(minHeight: screenSize.height)
    }
}

struct RecentSearchListView: View {
    @Binding var recentSearchList: [String]
    var onListItemSelected: (String) -> Void

    var body: some View {
        if recentSearchList.count > 0 {
            List {
                Section(header: Text("Recent Searches").font(.title2)) {
                    ForEach(recentSearchList, id: \.self) { search in
                        Button {
                            onListItemSelected(search)
                        } label: {
                            Text(search)
                                .font(.body)
                                .foregroundColor(Color("White"))
                        }

                    }
                }.textCase(nil)
            }.listStyle(InsetGroupedListStyle())
        } else {
            EmptyView()
        }
    }
}
