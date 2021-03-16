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
        FlowExtensionsKt.asCommonFlow(platformRepository.getPlatforms{ _,_ in })
    }
}
