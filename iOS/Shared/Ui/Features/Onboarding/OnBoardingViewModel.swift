//
//  OnBoardingViewModel.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import Foundation

let onBoardingCompleteKey = "onBoardingComplete"

class OnBoardingViewModel: ObservableObject {

     let preferences = UserDefaults.standard

     func isOnboardingComplete() -> Bool {
         return preferences.bool(forKey: onBoardingCompleteKey)
     }

     func setOnboardingAsComplete() {
         preferences.setValue(true, forKey: onBoardingCompleteKey)
     }
 }
