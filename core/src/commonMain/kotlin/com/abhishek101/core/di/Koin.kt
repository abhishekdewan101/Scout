package com.abhishek101.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun initKoin(appModules: List<Module>): KoinApplication {
    val combined = appModules.toMutableList().apply {
        add(platformModule)
        addAll(listOf(utilModule, dbModule, apiModule, repoModule, viewModelModule))
    }
    return startKoin {
        modules(combined)
    }
}

inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

expect val platformModule: Module
