//
//  MainAppNavigator.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core

enum MainAppDestination: String {
    case MainScreen = "MainScreen"
    case ExpandedListScreen = "ExpandedList"
    case DetailScreen = "DetailScreen"
}

struct MainAppNavigator: View {

    @State private var selection: String? = MainAppDestination.MainScreen.rawValue
    @State var listData: GameListData?
    @State var slug: String?

    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(
                        destination: LazyView(MainApp(navigateForward: navigateForward))
                                .navigationTitle("").navigationBarHidden(true),
                        tag: MainAppDestination.MainScreen.rawValue,
                        selection: $selection,
                        label: { EmptyView() })

                NavigationLink(
                        destination: LazyView(ExpandedList(listData: listData, navigateBack: {
                            self.navigateBack(destination: MainAppDestination.MainScreen) },
                                navigateForward: navigateForward)).navigationTitle("").navigationBarHidden(true),
                        tag: MainAppDestination.ExpandedListScreen.rawValue,
                        selection: $selection,
                        label: { EmptyView() })

                NavigationLink(
                        destination: LazyView(GameDetailScreen()).navigationTitle("").navigationBarHidden(true),
                        tag: MainAppDestination.DetailScreen.rawValue,
                        selection: $selection,
                        label: { EmptyView() })

            }
                    .navigationTitle("")
                    .navigationBarHidden(true)
        }
    }

    private func navigateBack(destination: MainAppDestination) {
        self.selection = destination.rawValue
    }

    private func navigateForward(destination: MainAppDestination, listData: GameListData?, slug: String?) {
        if destination == .ExpandedListScreen {
            if let data = listData {
                self.listData = data
                selection = MainAppDestination.ExpandedListScreen.rawValue
            }
        }

        if destination == .DetailScreen {
            if let gameSlug = slug {
                self.slug = gameSlug
                selection = MainAppDestination.DetailScreen.rawValue
            }
        }
    }
}
