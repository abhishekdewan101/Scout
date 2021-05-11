package com.abhishek101.gamescout.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.abhishek101.gamescout.features.genreselection.GenreSelectionViewModel
import com.abhishek101.gamescout.features.mainapp.details.GameDetailViewModel
import com.abhishek101.gamescout.features.mainapp.home.HomeScreenViewModel
import com.abhishek101.gamescout.features.mainapp.search.SearchScreenViewModel
import com.abhishek101.gamescout.features.mainapp.viewmore.ViewMoreViewModel
import com.abhishek101.gamescout.features.onboarding.OnBoardingNavigatorViewModel
import com.abhishek101.gamescout.features.onboarding.splash.SplashScreenVM
import com.abhishek101.gamescout.features.platformselection.PlatformSelectionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    single { SplashScreenVM(get()) }
    single { PlatformSelectionViewModel(get()) }
    single { GenreSelectionViewModel(get()) }
    single { OnBoardingNavigatorViewModel(get()) }
    single { HomeScreenViewModel(get()) }
    single { ViewMoreViewModel(get()) }
    single { GameDetailViewModel(get(), get()) }
    single { SearchScreenViewModel(get()) }

    // SharedPreferences
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "GameTrackerPrefKey",
            MODE_PRIVATE
        )
    }
}
