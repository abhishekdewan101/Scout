//
//  OnBoardingViewModel.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/21/21.
//

import Foundation

enum OnBoardingDestination {
    case PLATFORM
    case GENRE
    case HOMESCREEN
}


class OnBoardingViewModel: ObservableObject {
    @Published var navigationDestination: OnBoardingDestination
    
    init() {
        self.navigationDestination = OnBoardingDestination.PLATFORM
    }
    
    func updateNavigationDestination(destination: OnBoardingDestination) {
        self.navigationDestination = destination
    }
}
