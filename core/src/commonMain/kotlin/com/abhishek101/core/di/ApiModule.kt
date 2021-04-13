package com.abhishek101.core.di

import com.abhishek101.core.BuildKonfig
import com.abhishek101.core.remote.GameApi
import com.abhishek101.core.remote.GameApiImpl
import com.abhishek101.core.remote.GenreApi
import com.abhishek101.core.remote.GenreApiImpl
import com.abhishek101.core.remote.PlatformApi
import com.abhishek101.core.remote.PlatformApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val apiModule = module {
    single<PlatformApi> {
        PlatformApiImpl(get(), get())
    }
    single<GenreApi> {
        GenreApiImpl(get(), get())
    }
    single<GameApi> {
        GameApiImpl(get(), get())
    }
    single {
        HttpClient {
            val ktorLogger = get<Logger>()
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = ktorLogger
                level = if (BuildKonfig.EnableHttpLogging) LogLevel.ALL else LogLevel.NONE
            }
        }
    }
}
