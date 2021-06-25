//
//  HomeScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/20/21.
//

import SwiftUI
import ScoutCommon

struct HomeScreen: View {
    // swiftlint:disable:next force_cast
    let preferenceSelectionViewModel = koin.get(objCClass: PreferenceSelectionViewModel.self) as! PreferenceSelectionViewModel

    init() {
        // if you are seeing this that means onboarding was completed.
        preferenceSelectionViewModel.setOnBoardingCompleted()
    }

    var body: some View {
        Text("Hello, World! From The Home Screen")
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
