//
//  OnBoardingViewModel.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/21/21.
//

import Foundation

enum OnBoardingDestination: String {
    case PLATFORM = "Platform"
    case GENRE = "Genre"
    case HOMESCREEN = "HomeScreen"
}

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
