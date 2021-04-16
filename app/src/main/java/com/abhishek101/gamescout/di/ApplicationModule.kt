package com.abhishek101.gamescout.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.abhishek101.gamescout.features.genreselection.GenreSelectionViewModel
import com.abhishek101.gamescout.features.mainapp.home.HomeScreenViewModel
import com.abhishek101.gamescout.features.onboarding.MainNavigatorViewModel
import com.abhishek101.gamescout.features.onboarding.SplashViewModel
import com.abhishek101.gamescout.features.platformselection.PlatformSelectionViewModel
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
