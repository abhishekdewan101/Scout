//
//  ContentView.swift
//  Shared
//
//  Created by Abhishek Dewan on 3/8/21.
//

import SwiftUI
import core

struct ContentView: View {
    var body: some View {
        Text(Greeting().greeting())
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
