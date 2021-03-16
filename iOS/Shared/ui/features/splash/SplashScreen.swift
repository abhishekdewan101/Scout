//
//  ContentView.swift
//  Shared
//
//  Created by Abhishek Dewan on 3/8/21.
//

import SwiftUI

struct SplashScreen: View {
    
    @ObservedObject var splashScreenViewModel = SplashScreenViewModel()
    
    init() {
        splashScreenViewModel.authenticateUser()
    }
    
    var body: some View {
        if (splashScreenViewModel.isAuthenticationValid) {
            PlatformSelect()
        } else {
            VStack{
                Text("GameTracker")
                    .padding()
                    ProgressView()
            }
        }
    }
}

struct SplashScreen_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreen()
    }
}
