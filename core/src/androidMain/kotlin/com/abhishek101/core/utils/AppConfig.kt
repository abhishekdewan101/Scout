package com.abhishek101.core.utils

import com.abhishek101.core.BuildConfig

actual fun createAppConfig() = AppConfig(BuildConfig.ClientId, BuildConfig.ClientSecret)
