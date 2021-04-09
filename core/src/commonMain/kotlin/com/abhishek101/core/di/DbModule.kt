package com.abhishek101.core.di

import com.abhishek101.core.db.AppDb
import com.abhishek101.core.utils.DatabaseHelper
import org.koin.dsl.module

val dbModule = module {
    single {
        DatabaseHelper(AppDb(get()))
    }
}
