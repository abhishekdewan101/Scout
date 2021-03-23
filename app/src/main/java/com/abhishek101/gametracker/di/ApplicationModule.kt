package com.abhishek101.gametracker.di

import android.content.Context
import com.abhishek101.gametracker.ui.features.home.HomeScreenViewModel
import com.abhishek101.gametracker.ui.features.onboarding.OnBoardingViewModel
import com.abhishek101.gametracker.ui.features.onboarding.genre.GenreSelectionViewModel
import com.abhishek101.gametracker.ui.features.onboarding.platform.PlatformSelectionViewModel
import com.abhishek101.gametracker.ui.features.splash.SplashViewModel
import org.koin.dsl.module

val appModule = module {
    single { SplashViewModel(get()) }
    single { PlatformSelectionViewModel(get()) }
    single { GenreSelectionViewModel(get()) }
    single { (context: Context) -> OnBoardingViewModel(context) }
    single { HomeScreenViewModel(get()) }
}
