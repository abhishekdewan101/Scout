package com.abhishek101.core.utils

import platform.Foundation.NSUserDefaults

@Suppress("EmptyDefaultConstructor")
actual class KeyValueStore {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun getBoolean(key: String): Boolean {
        return userDefaults.boolForKey(defaultName = key)
    }

    actual fun setBoolean(key: String, value: Boolean) {
        return userDefaults.setBool(value = value, forKey = key)
    }

    actual fun setString(key: String, value: String) {
        userDefaults.setObject(value = value, forKey = key)
    }

    actual fun getString(key: String): String {
        return userDefaults.stringForKey(key) ?: ""
    }
}
