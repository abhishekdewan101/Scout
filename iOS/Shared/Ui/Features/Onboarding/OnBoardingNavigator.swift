//
//  OnBoardingNavigator.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 4/25/21.
//

import SwiftUI

enum OnBoardingDestinations {
    case platformSelectionScreen
    case splashScreen
    case genreSelectionScreen
    case mainAppScreen
}

struct OnBoardingNavigator: View {
    @State private var selection: OnBoardingDestinations = .splashScreen

    let viewModel = OnBoardingViewModel()

    var body: some View {
        switch selection {
        case .splashScreen: LazyView(SplashScreen {
            if viewModel.isOnboardingComplete() {
                withAnimation {
                    selection = .mainAppScreen
                }
            } else {
                withAnimation {
                    selection = .platformSelectionScreen
                }
            }
        })
        case .platformSelectionScreen: LazyView(PlatformSelection { withAnimation {selection = .genreSelectionScreen}}
                .transition(.move(edge: .trailing)))
        case .genreSelectionScreen: LazyView(GenreSelection {
            viewModel.setOnboardingAsComplete()
            withAnimation {
                selection = .mainAppScreen
            }
        }.transition(.move(edge: .trailing)))
        case .mainAppScreen: LazyView(MainApp().transition(.move(edge: .trailing)))
        }
    }
}
