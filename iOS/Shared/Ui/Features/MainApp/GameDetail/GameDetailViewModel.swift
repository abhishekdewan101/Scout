//
//  GameDetailViewModel.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/3/21.
//

import Foundation
import core

class GameDetailViewModel: ObservableObject {

    let gameRepository = koin.get(objCProtocol: GameRepository.self) as! GameRepository

    @Published var gameDetails: IgdbGameDetail?

    func getGameDetails(slug: String) {
        gameRepository.getGameDetailForSlug(slug: slug) { ( coreFlow: Kotlinx_coroutines_coreFlow?, _: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object: AnyObject?) in
                self.gameDetails = (object as! IgdbGameDetail)
            }
        }
    }
}
