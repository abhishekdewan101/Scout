//
//  GenreSelectionScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/22/21.
//

import SwiftUI
import ScoutCommon

struct GenreSelectionScreen: View {
    @Binding var preferenceSelectionStage: PreferenceSelectionStage

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: PreferenceSelectionViewModel.self) as! PreferenceSelectionViewModel

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
