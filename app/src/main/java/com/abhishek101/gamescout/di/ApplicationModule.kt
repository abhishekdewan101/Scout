package com.abhishek101.gamescout.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.abhishek101.core.utils.KeyValueStore
import com.abhishek101.gamescout.features.mainapp.library.LibraryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    single { LibraryViewModel(get()) }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "GameTrackerPrefKey",
            MODE_PRIVATE
        )
    }

    single {
        KeyValueStore(
            androidContext().getSharedPreferences(
                "GameTrackerPrefKey",
                MODE_PRIVATE
            )
        )
    }
}
