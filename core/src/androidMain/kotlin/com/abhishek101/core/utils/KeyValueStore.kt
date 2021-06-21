package com.abhishek101.core.utils

import android.content.SharedPreferences

actual class KeyValueStore(private val sharedPreferences: SharedPreferences) {

    actual fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    actual fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }
}
