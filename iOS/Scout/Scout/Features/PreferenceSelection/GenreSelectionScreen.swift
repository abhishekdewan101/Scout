//
//  GenreSelectionScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/22/21.
//

import SwiftUI

struct GenreSelectionScreen: View {
    @Binding var preferenceSelectionStage: PreferenceSelectionStage

    var body: some View {
        Text("Hello, World!").onTapGesture {
            preferenceSelectionStage = .home
        }
    }
}

struct GenreSelectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        GenreSelectionScreen(preferenceSelectionStage: Binding.constant(.genreSelection))
    }
}
