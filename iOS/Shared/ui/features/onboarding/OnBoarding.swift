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
                NavigationLink(destination: PlatformSelect {self.selection = "Genre" }.navigationTitle("").navigationBarHidden(true), tag: "Platform", selection: $selection) { EmptyView() }
                NavigationLink(destination: GenreSelection().navigationTitle("").navigationBarHidden(true), tag: "Genre", selection: $selection) { EmptyView()}
                NavigationLink(destination: HomeScreen().navigationTitle("").navigationBarHidden(true), tag: "Home", selection: $selection) { EmptyView()}
            }
        }.navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear {
            self.selection = "Platform"
        }
    }
}

struct OnBoarding_Previews: PreviewProvider {
    static var previews: some View {
        OnBoarding()
    }
}
