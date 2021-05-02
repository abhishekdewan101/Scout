//
//  OnBoardingNavigator.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 4/25/21.
//

import SwiftUI

enum OnBoardingDestinations: String {
    case PlatformSelectionScreen = "PlatformSelectionScreen"
    case SplashScreen = "SplashScreen"
    case GenreSelectionScreen = "GenreSelectionScreen"
    case MainAppScreen = "MainAppScreen"
}

struct OnBoardingNavigator: View {
    @State private var selection: String? = OnBoardingDestinations.SplashScreen.rawValue

    let viewModel = OnBoardingViewModel()

    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(
                    destination: LazyView(SplashScreen {
                        if viewModel.isOnboardingComplete() {
                            selection = OnBoardingDestinations.MainAppScreen.rawValue
                        } else {
                            selection = OnBoardingDestinations.PlatformSelectionScreen.rawValue
                        }
                        return EmptyView()
                    }).navigationTitle("").navigationBarHidden(true),
                    tag: OnBoardingDestinations.SplashScreen.rawValue,
                    selection: $selection,
                    label: {EmptyView()})

                NavigationLink(
                    destination: LazyView(PlatformSelection {
                        selection = OnBoardingDestinations.GenreSelectionScreen.rawValue
                    }).navigationTitle("").navigationBarHidden(true),
                    tag: OnBoardingDestinations.PlatformSelectionScreen.rawValue,
                    selection: $selection,
                    label: {EmptyView()})

                NavigationLink(
                    destination: LazyView(GenreSelection {
                        viewModel.setOnboardingAsComplete()
                        selection = OnBoardingDestinations.MainAppScreen.rawValue
                    }).navigationTitle("").navigationBarHidden(true),
                    tag: OnBoardingDestinations.GenreSelectionScreen.rawValue,
                    selection: $selection,
                    label: {EmptyView()})

                NavigationLink(destination: LazyView(MainAppNavigator()).navigationTitle("").navigationBarHidden(true),
                               tag: OnBoardingDestinations.MainAppScreen.rawValue,
                               selection: $selection,
                               label: {EmptyView()})
            }
            .navigationTitle("")
            .navigationBarHidden(true)
            .onAppear {

            }
        }
    }
}
