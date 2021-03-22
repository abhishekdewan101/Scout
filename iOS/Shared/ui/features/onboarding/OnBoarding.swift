//
//  OnBoarding.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/21/21.
//

import SwiftUI

struct OnBoarding: View {
    
    let viewModel = OnBoardingViewModel()
    
    @State private var selection: String? = nil
    
    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(destination: PlatformSelect {self.selection = OnBoardingDestination.GENRE.rawValue }.navigationTitle("").navigationBarHidden(true), tag: OnBoardingDestination.PLATFORM.rawValue, selection: $selection) { EmptyView() }
                NavigationLink(destination: GenreSelection{
                    self.viewModel.setOnboardingAsComplete()
                    self.selection = OnBoardingDestination.HOMESCREEN.rawValue
                }.navigationTitle("").navigationBarHidden(true), tag: OnBoardingDestination.GENRE.rawValue, selection: $selection) { EmptyView()}
                NavigationLink(destination: HomeScreen().navigationTitle("").navigationBarHidden(true), tag: OnBoardingDestination.HOMESCREEN.rawValue, selection: $selection) { EmptyView()}
            }
        }.navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear {
            if (self.viewModel.isOnboardingComplete()) {
                self.selection = OnBoardingDestination.HOMESCREEN.rawValue
            } else {
                self.selection = OnBoardingDestination.PLATFORM.rawValue
            }
        }
    }
}

struct OnBoarding_Previews: PreviewProvider {
    static var previews: some View {
        OnBoarding()
    }
}
