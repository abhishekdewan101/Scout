package com.abhishek101.core.di

import com.abhishek101.core.db.AppDb
import com.abhishek101.core.db.Library
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.EnumColumnAdapter
import org.koin.dsl.module

val dbModule = module {
    single {
        DatabaseHelper(
            AppDb(
                get(),
                LibraryAdapter = Library.Adapter(
                    statusAdapter = EnumColumnAdapter()
                )
            )
        )
    }
}
