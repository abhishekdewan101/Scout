package com.abhishek101.core.utils

import com.abhishek101.core.BuildKonfig

data class AppConfig(
    val clientId: String
)

fun defaultAppConfig() = AppConfig(BuildKonfig.ClientId)
