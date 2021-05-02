//
//  MainApp.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI
import core

struct MainApp: View {

    var navigateForward: (MainAppDestination, GameListData?, String?) -> Void

    init(navigateForward: @escaping (MainAppDestination, GameListData?, String?) -> Void) {
        self.navigateForward = navigateForward
    }

    var body: some View {
        TabView {
            HomeScreen(navigateForward: navigateForward).tabItem {
                Label("Home", systemImage: "house")
            }
            SearchScreen().tabItem {
                Label("Search", systemImage: "magnifyingglass")
            }
            ProfileScreen().tabItem {
                Label("Profile", systemImage: "person.crop.circle")
            }
        }.onAppear {
            UITabBar.appearance().barTintColor = UIColor.init(Color.blue)
            UITabBar.appearance().unselectedItemTintColor = UIColor.init(Color.black)
            UITabBar.appearance().isTranslucent = false
        }.accentColor(.white)
    }
}

struct MainApp_Previews: PreviewProvider {
    static var previews: some View {
        MainApp { _, _, _ in

        }
    }
}
