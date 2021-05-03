//
//  ContentView.swift
//  Shared
//
//  Created by Abhishek Dewan on 3/8/21.
//

import SwiftUI

struct SplashScreen: View {

    var navigateForward: () -> Void

    @ObservedObject var viewModel = SplashScreenViewModel()

    init(navigateForward: @escaping () -> Void) {
        self.navigateForward = navigateForward
    }

    var body: some View {
            ZStack {
                Image("background").resizable().aspectRatio(contentMode: .fill).edgesIgnoringSafeArea(.all)
                Rectangle().fill(Color.black.opacity(0.5)).edgesIgnoringSafeArea(.all)
                VStack {
                    Text("Scout")
                            .font(.system(size: 60.0))
                            .fontWeight(.bold)
                            .foregroundColor(.blue)
                    ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: Color.white))
                            .scaleEffect(x: 2, y: 2, anchor: .center)
                }
            }.onChange(of: viewModel.isAuthenticationValid) { authenticationValid in
                if authenticationValid {
                    navigateForward()
                }
            }.onAppear {
                viewModel.authenticateUser()
            }
    }
}
