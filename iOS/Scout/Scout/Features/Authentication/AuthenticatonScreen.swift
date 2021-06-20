//
//  AuthenticatonScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/19/21.
//

import SwiftUI
import ScoutCommon

struct AuthenticatonScreen: View {
    
    @State private var isAuthenticated = false
    
    let viewModel = koin.get(objCClass: AuthenticationViewModel.self) as! AuthenticationViewModel
    
    var body: some View {
        if (isAuthenticated) {
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
                viewModel.checkAuthentication { authenticationFound in
                    isAuthenticated = authenticationFound.boolValue
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
