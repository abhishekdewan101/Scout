package com.abhishek101.gametracker.utils

import com.abhishek101.gametracker.BuildConfig

data class AppConfig(
    val clientId: String = BuildConfig.ClientId,
    val clientSecret: String = BuildConfig.ClientSecret
)
