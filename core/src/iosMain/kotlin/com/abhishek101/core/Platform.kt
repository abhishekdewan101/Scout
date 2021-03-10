package com.abhishek101.core

import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = "Hahahahaha" +
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
