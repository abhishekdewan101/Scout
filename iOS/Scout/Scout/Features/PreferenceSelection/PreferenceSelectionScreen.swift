//
//  OnboardingScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/24/21.
//

import SwiftUI

enum PreferenceSelectionStage {
    case platformSelection, genreSelection, home
}

struct PreferenceSelectionScreen: View {
    @State private var stage: PreferenceSelectionStage = .platformSelection
    var body: some View {
        Group {
            switch stage {
            case .platformSelection: PlatformSelectionScreen(preferenceSelectionStage: $stage)
            case .genreSelection: GenreSelectionScreen(preferenceSelectionStage: $stage)
            case .home: HomeScreen()
            }
        }
    }
}

struct OnboardingScreen_Previews: PreviewProvider {
    static var previews: some View {
        PreferenceSelectionScreen()
    }
}
