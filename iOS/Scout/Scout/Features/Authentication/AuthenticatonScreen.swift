//
//  AuthenticatonScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/19/21.
//

import SwiftUI
import ScoutCommon

struct AuthenticatonScreen: View {
    
    @State private var authenicatonState : AuthenticationState = AuthenticationState(isAuthenticated: false, isOnboardingCompleted: false)
    
    let viewModel = koin.get(objCClass: AuthenticationViewModel.self) as! AuthenticationViewModel
    
    var body: some View {
        if (authenicatonState.isAuthenticated && authenicatonState.isOnboardingCompleted) {
            HomeScreen()
        } else if (authenicatonState.isAuthenticated && !authenicatonState.isOnboardingCompleted) {
            PlatformSelectionScreen()
        } else {
            ZStack {
                Color("BackgroundPurple").ignoresSafeArea(.all)
                VStack {
                    Image("Logo")
                    ProgressView()
                        .scaleEffect(x: 2, y: 2 , anchor: .center)
                        .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
                }
            }.onAppear {
                viewModel.checkAuthentication { state in
                    authenicatonState = state
                }
            }
        }
        
    }
}

struct AuthenticatonScreen_Previews: PreviewProvider {
    static var previews: some View {
        AuthenticatonScreen()
    }
}
