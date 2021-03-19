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
        SplashScreenContent(isAuthenticationValid: splashScreenViewModel.isAuthenticationValid)
    }
    
}

struct SplashScreenContent: View {
    var isAuthenticationValid: Bool
    
    var body: some View {
        if (isAuthenticationValid) {
            PlatformSelect()
        } else {
            VStack{
                Text("GameTracker")
                    .padding()
                    .foregroundColor(Color("Primary"))
                ProgressView().foregroundColor(Color("Primary"))
            }.background(Color("Background"))
        }
    }
}

struct SplashScreen_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreenContent(isAuthenticationValid: false)
    }
}
