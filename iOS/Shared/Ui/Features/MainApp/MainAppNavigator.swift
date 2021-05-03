//
//  MainAppNavigator.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core

enum MainAppDestination: String {
    case mainScreen = "MainScreen"
    case expandedListScreen = "ExpandedList"
    case detailScreen = "DetailScreen"
}

struct MainAppNavigator: View {

    @State private var selection: String? = MainAppDestination.mainScreen.rawValue
    @State var listData: GameListData?
    @State var slug: String?

    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(
                        destination: LazyView(MainApp(navigateForward: navigateForward))
                                .navigationTitle("").navigationBarHidden(true),
                        tag: MainAppDestination.mainScreen.rawValue,
                        selection: $selection,
                        label: { EmptyView() })

                NavigationLink(
                        destination: LazyView(ExpandedList(listData: listData, navigateBack: {
                            self.navigateBack(destination: MainAppDestination.mainScreen) },
                                navigateForward: navigateForward)).navigationTitle("").navigationBarHidden(true),
                        tag: MainAppDestination.expandedListScreen.rawValue,
                        selection: $selection,
                        label: { EmptyView() })

                NavigationLink(
                        destination: LazyView(GameDetailScreen()).navigationTitle("").navigationBarHidden(true),
                        tag: MainAppDestination.detailScreen.rawValue,
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
        if destination == .expandedListScreen {
            if let data = listData {
                self.listData = data
                selection = MainAppDestination.expandedListScreen.rawValue
            }
        }

        if destination == .detailScreen {
            if let gameSlug = slug {
                self.slug = gameSlug
                selection = MainAppDestination.detailScreen.rawValue
            }
        }
    }
}
