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
}

struct OnBoardingNavigator: View {
    @State private var selection: String? = OnBoardingDestinations.SplashScreen.rawValue
    
    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(
                    destination: SplashScreen(){
                        selection = OnBoardingDestinations.PlatformSelectionScreen.rawValue
                        return EmptyView()
                    }.navigationTitle("").navigationBarHidden(true),
                    tag: OnBoardingDestinations.SplashScreen.rawValue,
                    selection: $selection,
                    label: {EmptyView()})
                
                NavigationLink(
                    destination: PlatformSelection().navigationTitle("").navigationBarHidden(true),
                    tag: OnBoardingDestinations.PlatformSelectionScreen.rawValue,
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
