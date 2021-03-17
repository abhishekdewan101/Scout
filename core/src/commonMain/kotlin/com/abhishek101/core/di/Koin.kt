package com.abhishek101.core.di

import com.abhishek101.core.db.AppDb
import com.abhishek101.core.remote.AuthenticationApi
import com.abhishek101.core.remote.AuthenticationApiImpl
import com.abhishek101.core.remote.PlatformApi
import com.abhishek101.core.remote.PlatformApiImpl
import com.abhishek101.core.repositories.AuthenticationRepository
import com.abhishek101.core.repositories.AuthenticationRepositoryImpl
import com.abhishek101.core.repositories.PlatformRepository
import com.abhishek101.core.repositories.PlatformRepositoryImpl
import com.abhishek101.core.utils.DatabaseHelper
import com.abhishek101.core.utils.KtorLogger
import com.abhishek101.core.utils.defaultAppConfig
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
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
        get<DatabaseHelper>().authenticationQueries
    }
    single {
        get<DatabaseHelper>().platformQueries
    }
    single {
        DatabaseHelper(AppDb(get()))
    }
    single<AuthenticationApi> {
        AuthenticationApiImpl(get())
    }
    single<PlatformApi> {
        PlatformApiImpl(get(), get())
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
                level = LogLevel.ALL
            }
        }
    }
    single<Logger> {
        KtorLogger(getWith("Ktor"))
    }
    single {
        defaultAppConfig()
    }
    single<PlatformRepository> {
        PlatformRepositoryImpl(get(), get(), get())
    }
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get(), get())
    }
}

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

expect val platformModule: Module
