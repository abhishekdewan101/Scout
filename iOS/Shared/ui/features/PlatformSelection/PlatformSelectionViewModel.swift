//
//  PlatformSelectionViewModel.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/26/21.
//

import Foundation
import core

class PlatformSelectViewModel : ObservableObject {
    @Published var isLoading: Bool
    @Published var platformList: [Platform]
    
    init() {
        self.isLoading = true
        self.platformList = []
        getPlatforms()
    }
    
    let platformRepository = koin.get(objCProtocol: PlatformRepository.self) as! PlatformRepository
    
    func getPlatforms() {
        FlowExtensionsKt.asCommonFlow(platformRepository.getCachedPlatforms()).watch(block: { [self] platforms in
            if (platforms?.count == 0) {
                self.platformRepository.updateCachedPlatforms(completionHandler: {_,_ in})
            } else {
                isLoading = false
                platformList = (platforms as? [Platform])!
            }
        })
    }
    
    func setPlatformAsFavorite(platform: Platform, isOwned: Bool) {
        platformRepository.setPlatformAsOwned(slug: platform.slug, isOwned: isOwned)
    }
    
    func getOwnedPlatformCount() -> Int {
        return platformList.filter { $0.isOwned == true }.count
    }
}
