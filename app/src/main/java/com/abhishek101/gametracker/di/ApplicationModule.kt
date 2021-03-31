package com.abhishek101.gametracker.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.abhishek101.gametracker.ui.components.navigation.MainNavigatorViewModel
import com.abhishek101.gametracker.ui.features.genreselection.GenreSelectionViewModel
import com.abhishek101.gametracker.ui.features.home.HomeScreenViewModel
import com.abhishek101.gametracker.ui.features.platformselection.PlatformSelectionViewModel
import com.abhishek101.gametracker.ui.features.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    single { SplashViewModel(get()) }
    single { PlatformSelectionViewModel(get()) }
    single { GenreSelectionViewModel(get()) }
    single { MainNavigatorViewModel(get()) }
    single { HomeScreenViewModel(get()) }

    // SharedPreferences
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "GameTrackerPrefKey",
            MODE_PRIVATE
        )
    }
}
