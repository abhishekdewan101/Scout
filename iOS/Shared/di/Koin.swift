//
//  Koin.swift
//  GameTracker
//
//  Created by Abhishek Dewan on 3/12/21.
//

import Foundation
import core

private var _koin: Koin_coreKoin? = nil

var koin: Koin_coreKoin {
    if (_koin == nil) {
        startKoin()
    }
    return _koin!
}

func startKoin() {
    let koinApplication = KoinIosKt.doInitKoinForIos()
    _koin = koinApplication.koin
}
