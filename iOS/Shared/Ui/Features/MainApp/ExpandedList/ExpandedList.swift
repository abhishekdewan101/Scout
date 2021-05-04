//
//  ExpandedList.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core

struct ExpandedList: View {

    var listData: GameListData

    init(listData: GameListData) {
        self.listData = listData
    }

    var body: some View {
        ZStack {
            Color.black.edgesIgnoringSafeArea(.all)
            ScrollView {
                let games = listData.games
                let imageIdList = games.filter { $0.cover != nil }.map { $0.cover!.qualifiedUrl }

                GamePosterGrid(imageIdList: imageIdList.map { $0 }) { index in
                    return games[index].slug
                }

            }.padding(.top)
        }.navigationBarItems(leading: Text(listData.title)
                                .font(.title)
                                .fontWeight(.bold)
                                .foregroundColor(Color.init("TitleColor")))
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarColor(.black)
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
