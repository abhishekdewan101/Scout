package com.abhishek101.core.di

import com.abhishek101.core.db.AppDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun initKoinForIos(): KoinApplication = initKoin(module { })

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(AppDb.Schema, "appDb") }
}
