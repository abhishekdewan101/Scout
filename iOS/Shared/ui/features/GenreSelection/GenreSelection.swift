//
//  GenreSelection.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI

struct GenreSelection: View {
    
    var navigateForward: () -> EmptyView
    
    init(navigateForward: @escaping () -> EmptyView) {
        self.navigateForward = navigateForward
    }
    
    var body: some View {
        Text("Genre Selection Screen")
    }
}

struct GenreSelection_Previews: PreviewProvider {
    static var previews: some View {
        GenreSelection() {
            return EmptyView()
        }
    }
}
