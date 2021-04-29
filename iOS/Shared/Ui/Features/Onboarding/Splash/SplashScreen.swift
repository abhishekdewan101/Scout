//
//  ContentView.swift
//  Shared
//
//  Created by Abhishek Dewan on 3/8/21.
//

import SwiftUI

struct SplashScreen: View {
    
    var navigateForward: () -> EmptyView
    
    @ObservedObject var splashScreenViewModel = SplashScreenViewModel()
    
    init(navigateForward: @escaping () -> EmptyView) {
        self.navigateForward = navigateForward
        splashScreenViewModel.authenticateUser()
    }
    
    var body: some View {
        SplashScreenContent(isAuthenticationValid: splashScreenViewModel.isAuthenticationValid, navigateForward: navigateForward)
    }
    
}

struct SplashScreenContent: View {
    var isAuthenticationValid: Bool
    var navigateForward: () -> EmptyView
    
    var body: some View {
        if (isAuthenticationValid) {
            navigateForward()
        } else {
            ZStack{
                Image("background").resizable().aspectRatio(contentMode: .fill).edgesIgnoringSafeArea(.all)
                Rectangle().fill(Color.black.opacity(0.5)).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/)
                
                VStack {
                    Text("Scout")
                        .font(.system(size: 60.0))
                        .fontWeight(.bold)
                        .foregroundColor(.blue)
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: Color.white))
                        .scaleEffect(x: 2, y: 2, anchor: .center)
                }
            }
        }
    }
}

struct SplashScreen_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreenContent(isAuthenticationValid: false) {
            EmptyView()
        }
    }
}
