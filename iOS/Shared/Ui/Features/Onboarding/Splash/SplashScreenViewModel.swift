//
//  SplashScreenViewModel.swift
//  GameTracker
//
//  Created by Abhishek Dewan on 3/12/21.
//

import Foundation
import core

class SplashScreenViewModel: Injectable, ObservableObject {
    @Published var isAuthenticationValid: Bool

    let authenticationRepository = koin.get(objCProtocol: AuthenticationRepository.self) as! AuthenticationRepository

    init() {
        self.isAuthenticationValid = false
    }

    func authenticateUser() {
        FlowExtensionsKt.asCommonFlow(authenticationRepository.getAuthenticationData())
            .watch { result in
                DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                    let auth = result as? Authentication
                    if auth != nil {
                        print("Found a valid authentication - Can proceed")
                        self.isAuthenticationValid = true
                    } else {
                        print("Didn't find a valid authentication")
                        self.authenticationRepository.authenticateUser {_, _ in }
                    }
                }

            }
    }
}
