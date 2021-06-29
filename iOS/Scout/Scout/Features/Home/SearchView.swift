//
//  SearchView.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/27/21.
//

import SwiftUI

struct SearchView: View {

    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            SearchBar()
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

    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .foregroundColor(Color.white)
                .padding(.vertical)
                .padding(.leading)
            TextField("Enter game name",
                      text: $searchTerm,
                      onCommit: {
                        print("Search for \(searchTerm)")
                      }
            )
            Spacer()
        }
        .background(Color("BrandBackground"))
        .cornerRadius(10)
        .padding()
    }
}
