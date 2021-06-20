//
//  Koin.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/20/21.
//

import Foundation
import ScoutCommon

func startKoin() {
    // You could just as easily define all these dependencies in Kotlin, but this helps demonstrate how you might pass platform-specific dependencies in a larger scale project where declaring them in Kotlin is more difficult, or where they're also used in iOS-specific code.
    
    let koinApplication = KoinIosKt.doInitKoinForIos()
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    if _koin == nil {
        startKoin()
    }
    return _koin!
}
