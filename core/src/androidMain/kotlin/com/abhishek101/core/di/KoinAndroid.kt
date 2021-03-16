package com.abhishek101.core.di

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.abhishek101.core.db.AppDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(AppDb.Schema, get(), "appDb")
    }

    val baseKermit = Kermit(LogcatLogger()).withTag("KampKit")
    factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
}
