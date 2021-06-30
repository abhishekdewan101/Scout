package com.abhishek101.core.utils

import android.content.SharedPreferences

actual class KeyValueStore(private val sharedPreferences: SharedPreferences) {

    actual fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    actual fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    actual fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    actual fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }
}
