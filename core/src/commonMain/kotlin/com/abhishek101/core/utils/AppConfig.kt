package com.abhishek101.core.utils

data class AppConfig(val clientId: String, val clientSecret: String)

expect fun createAppConfig(): AppConfig
