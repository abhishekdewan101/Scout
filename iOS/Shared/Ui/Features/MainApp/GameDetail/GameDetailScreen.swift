//
//  GameDetailScreen.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI

struct GameDetailScreen: View {

    var gameSlug: String

    init(gameSlug: String) {
        self.gameSlug = gameSlug
    }

    var body: some View {
        ZStack {
            Color.black
            VStack {
                Text("Game Details For -> \(gameSlug)").foregroundColor(Color.white)
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        }.edgesIgnoringSafeArea(.all)
    }
}
