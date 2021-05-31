package com.abhishek101.core.di

import com.abhishek101.core.utils.KtorLogger
import com.abhishek101.core.utils.defaultAppConfig
import io.ktor.client.features.logging.Logger
import kotlinx.datetime.Clock
import org.koin.dsl.module

val utilModule = module {
    single<Logger> {
        KtorLogger(getWith("Ktor"))
    }
    single {
        defaultAppConfig()
    }
    single<Clock> {
        Clock.System
    }
}
