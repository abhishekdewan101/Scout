//
//  HomeScreenViewModel.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/29/21.
//

import Foundation
import core

class HomeScreenViewModel: ObservableObject {
    
    let gameRepository = koin.get(objCProtocol: GameRepository.self) as! GameRepository
    
    @Published var showcaseListData : GameListData = GameListData(title: "", games: [])
    
    init() {
        getShowcaseGames()
    }
    
    private func getShowcaseGames() {
        gameRepository.getListDataForType(type: ListType.showcase) { ( coreFlow : Kotlinx_coroutines_coreFlow?, error: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object : AnyObject?) in
                self.showcaseListData = (object as! GameListData)
            }
        }
    }
}
