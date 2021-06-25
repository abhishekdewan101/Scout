//
//  Koin.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/20/21.
//

import Foundation
import ScoutCommon

func startKoin() {

    let koinApplication = KoinIosKt.doInitKoinForIos()
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin?
var koin: Koin_coreKoin {
    if _koin == nil {
        startKoin()
    }
    // swiftlint:disable:next force_unwrapping
    return _koin!
}
