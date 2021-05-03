//
//  OnBoardingNavigator.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 4/25/21.
//

import SwiftUI

enum OnBoardingDestinations {
    case PlatformSelectionScreen
    case SplashScreen
    case GenreSelectionScreen
    case MainAppScreen
}

struct OnBoardingNavigator: View {
    @State private var selection: OnBoardingDestinations = .SplashScreen

    let viewModel = OnBoardingViewModel()

    var body: some View {
        switch selection {
        case .SplashScreen: LazyView(SplashScreen {
            if viewModel.isOnboardingComplete() {
                withAnimation {
                    selection = .MainAppScreen
                }
            } else {
                withAnimation {
                    selection = .PlatformSelectionScreen
                }
            }
        })
        case .PlatformSelectionScreen: LazyView(PlatformSelection { withAnimation {selection = .GenreSelectionScreen}}
                .transition(.move(edge: .trailing)))
        case .GenreSelectionScreen: LazyView(GenreSelection {
            viewModel.setOnboardingAsComplete()
            withAnimation {
                selection = .MainAppScreen
            }
        }.transition(.move(edge: .trailing)))
        case .MainAppScreen: LazyView(MainAppNavigator().transition(.move(edge: .trailing)))
        }
    }
}
