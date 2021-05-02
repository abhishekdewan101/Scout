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

    @Published var showcaseListData: GameListData = GameListData(title: "", games: [])
    @Published var comingSoonData: GameListData = GameListData(title: "", games: [])
    @Published var recentGameData: GameListData = GameListData(title: "", games: [])
    @Published var hypedGameData: GameListData = GameListData(title: "", games: [])
    @Published var topRatedGameData: GameListData = GameListData(title: "", games: [])

    init() {
        getShowcaseGames()
        getComingSoonGames()
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.getRecentGames()
            self.getHypedGames()
            self.getTopRatedGames()
        }
    }

    private func getShowcaseGames() {
        gameRepository.getListDataForType(type: ListType.showcase) { ( coreFlow: Kotlinx_coroutines_coreFlow?, _: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object: AnyObject?) in
                self.showcaseListData = (object as! GameListData)
            }
        }
    }

    private func getComingSoonGames() {
        gameRepository.getListDataForType(type: ListType.comingSoon) { (coreFlow: Kotlinx_coroutines_coreFlow?, _: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object: AnyObject?) in
                self.comingSoonData = (object as! GameListData)
            }
        }
    }

    private func getRecentGames() {
        gameRepository.getListDataForType(type: ListType.recent) { (coreFlow: Kotlinx_coroutines_coreFlow?, _: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object: AnyObject?) in
                self.recentGameData = (object as! GameListData)
            }
        }
    }

    private func getHypedGames() {
        gameRepository.getListDataForType(type: ListType.mostHyped) { (coreFlow: Kotlinx_coroutines_coreFlow?, _: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object: AnyObject?) in
                self.hypedGameData = (object as! GameListData)
            }
        }
    }

    private func getTopRatedGames() {
        gameRepository.getListDataForType(type: ListType.topRated) { (coreFlow: Kotlinx_coroutines_coreFlow?, _: Error?) in
            FlowExtensionsKt.asCommonFlow(coreFlow!).watch { (object: AnyObject?) in
                self.topRatedGameData = (object as! GameListData)
            }
        }
    }
}
