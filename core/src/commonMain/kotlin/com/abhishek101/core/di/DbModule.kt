package com.abhishek101.core.di

import com.abhishek101.core.db.AppDb
import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import org.koin.dsl.module

val platformAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",")
        }

    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}

val dbModule = module {
    single {
        DatabaseHelper(
            AppDb(
                get(),
                LibraryGameAdapter = LibraryGame.Adapter(
                    statusAdapter = EnumColumnAdapter(),
                    platformAdapter = platformAdapter
                )
            )
        )
    }
}
