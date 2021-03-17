//
//  PlatformSelectViewModel.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/16/21.
//

import Foundation
import core

class PlatformSelectViewModel : ObservableObject {
    @Published var isLoading: Bool
    @Published var platformList: [Platforms]
    
    init() {
        self.isLoading = false
        self.platformList = []
    }
    
    let platformRepository = koin.get(objCProtocol: PlatformRepository.self) as! PlatformRepository
    
    func getPlatforms() {
        FlowExtensionsKt.asCommonFlow(platformRepository.getPlatforms()).watch(block: { [self] platforms in
            if (platforms?.count == 0) {
                self.platformRepository.getPlatformsAndUpdate(completionHandler: {_,_ in})
            } else {
                isLoading = false
                platformList = (platforms as? [Platforms])!
            }
        })
    }
    
    func setPlatformAsFavorite(platform: Platforms, isFavorite: Bool) {
        platformRepository.updateFavoritePlatform(platform: platform, isFavorite: isFavorite)
    }
}
