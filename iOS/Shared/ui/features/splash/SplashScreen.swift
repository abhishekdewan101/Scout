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
            OnBoarding()
        } else {
            VStack{
                Text("GameTracker")
                    .padding()
                    .foregroundColor(Color("Primary"))
                    .padding(.top)
                    .font(.largeTitle)
                ProgressView().progressViewStyle(CircularProgressViewStyle(tint: Color("Primary")))
                    .scaleEffect(2, anchor: .center)
            }
        }
    }
}

struct SplashScreen_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreenContent(isAuthenticationValid: false).preferredColorScheme(.dark)
    }
}
