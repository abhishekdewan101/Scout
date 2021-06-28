//
//  GameDetailScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/28/21.
//

import SwiftUI

struct GameDetailScreen: View {
    var slug: String
    var body: some View {
        Text("Hello, World! from Game Detail Screen for \(slug)")
    }
}

struct GameDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        GameDetailScreen(slug: "Something")
    }
}
