package com.abhishek101.core.utils

import co.touchlab.kermit.Kermit
import io.ktor.client.features.logging.Logger

class KtorLogger(private val kermit: Kermit) : Logger {
    override fun log(message: String) {
        kermit.d { message }
    }
}