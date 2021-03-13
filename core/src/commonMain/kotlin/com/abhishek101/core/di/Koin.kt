package com.abhishek101.core.di

import com.abhishek101.core.db.AppDb
import com.abhishek101.core.remote.AuthenticationApi
import com.abhishek101.core.remote.AuthenticationApiImpl
import com.abhishek101.core.repositories.AuthenticationRepository
import com.abhishek101.core.repositories.AuthenticationRepositoryImpl
import com.abhishek101.core.utils.createAppConfig
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun initKoin(appModules: List<Module>): KoinApplication {
    val combined = appModules.toMutableList().apply {
        add(platformModule)
        add(coreModule)
    }
    return startKoin {
        modules(combined)
    }
}

@ExperimentalTime
val coreModule: Module = module {
    single {
        AppDb(get()).authenticationQueries
    }
    single<AuthenticationApi> {
        AuthenticationApiImpl(get(), createAppConfig())
    }
    single {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get(), get())
    }
}

expect val platformModule: Module
