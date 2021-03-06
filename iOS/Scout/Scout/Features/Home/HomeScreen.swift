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
        UITabBar.appearance().backgroundColor = UIColor(named: "BrandBackground")
    }

    var body: some View {
        ZStack {
            Color("Black").edgesIgnoringSafeArea(.all)
            TabView {
                NavigationView {
                    GameListView()
                        .navigationBarTitle("Home")
                }.tabItem {
                    Label("Home", systemImage: "house")
                }
                NavigationView {
                    SearchView()
                        .navigationBarTitle("Search")
                }.tabItem {
                    Label("Search", systemImage: "magnifyingglass")
                }
                NavigationView {
                    LibraryView()
                        .navigationBarTitle("Library")
                }.tabItem {
                    Label("Library", systemImage: "gamecontroller")
                }

            }.accentColor(Color("White"))
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
