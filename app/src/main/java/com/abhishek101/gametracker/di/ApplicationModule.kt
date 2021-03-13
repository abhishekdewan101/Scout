package com.abhishek101.gametracker.di

import com.abhishek101.gametracker.ui.features.splash.SplashViewModel
import org.koin.dsl.module

val appModule = module {
    single { SplashViewModel(get()) }
}
