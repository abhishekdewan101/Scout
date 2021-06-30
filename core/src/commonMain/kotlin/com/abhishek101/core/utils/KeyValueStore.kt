package com.abhishek101.core.utils

expect class KeyValueStore {
    fun getBoolean(key: String): Boolean
    fun setBoolean(key: String, value: Boolean)
    fun setString(key: String, value: String)
    fun getString(key: String): String
}
