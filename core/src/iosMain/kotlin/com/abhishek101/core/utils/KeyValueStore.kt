package com.abhishek101.core.utils

import platform.Foundation.NSUserDefaults

actual class KeyValueStore() {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun getBoolean(key: String): Boolean {
        return userDefaults.boolForKey(defaultName = key)
    }

    actual fun setBoolean(key: String, value: Boolean) {
        return userDefaults.setBool(value = value, forKey = key)
    }
}
