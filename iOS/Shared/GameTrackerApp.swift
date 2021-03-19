//
//  GameTrackerApp.swift
//  Shared
//
//  Created by Abhishek Dewan on 3/8/21.
//

import SwiftUI

@main
struct GameTrackerApp: App {
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                Color("Background").edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/)
                SplashScreen()
            }
        }
    }
}
