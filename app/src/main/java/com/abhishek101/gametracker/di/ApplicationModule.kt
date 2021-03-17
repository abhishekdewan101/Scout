package com.abhishek101.gametracker.di

import com.abhishek101.gametracker.ui.features.genre.GenreSelectionViewModel
import com.abhishek101.gametracker.ui.features.platform.PlatformSelectionViewModel
import com.abhishek101.gametracker.ui.features.splash.SplashViewModel
import org.koin.dsl.module

val appModule = module {
    single { SplashViewModel(get()) }
    single { PlatformSelectionViewModel(get()) }
    single { GenreSelectionViewModel(get()) }
}
