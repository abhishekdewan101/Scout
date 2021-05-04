//
//  MainApp.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI
import core

struct MainApp: View {

    var body: some View {
        NavigationView {
            TabView {
                HomeScreen().tabItem {
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
            .navigationBarTitle("")
        }.accentColor(.white)
    }
}

struct MainApp_Previews: PreviewProvider {
    static var previews: some View {
        MainApp()
    }
}

struct NavigationBarModifier: ViewModifier {

    var backgroundColor: UIColor?

    init( backgroundColor: UIColor?) {
        self.backgroundColor = backgroundColor
        let coloredAppearance = UINavigationBarAppearance()
        coloredAppearance.configureWithTransparentBackground()
        coloredAppearance.backgroundColor = .clear
        coloredAppearance.titleTextAttributes = [.foregroundColor: UIColor.white]
        coloredAppearance.largeTitleTextAttributes = [.foregroundColor: UIColor.white]

        UINavigationBar.appearance().standardAppearance = coloredAppearance
        UINavigationBar.appearance().compactAppearance = coloredAppearance
        UINavigationBar.appearance().scrollEdgeAppearance = coloredAppearance
        UINavigationBar.appearance().tintColor = .white

    }

    func body(content: Content) -> some View {
        ZStack {
            content
            VStack {
                GeometryReader { geometry in
                    Color(self.backgroundColor ?? .clear)
                        .frame(height: geometry.safeAreaInsets.top)
                        .edgesIgnoringSafeArea(.top)
                    Spacer()
                }
            }
        }
    }
}

extension View {

    func navigationBarColor(_ backgroundColor: UIColor?) -> some View {
        self.modifier(NavigationBarModifier(backgroundColor: backgroundColor))
    }

}
