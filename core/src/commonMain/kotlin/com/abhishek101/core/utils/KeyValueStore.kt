package com.abhishek101.core.utils

expect class KeyValueStore {
    fun getBoolean(key: String): Boolean
    fun setBoolean(key: String, value: Boolean)
}
