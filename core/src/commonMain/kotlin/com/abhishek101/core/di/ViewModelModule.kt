package com.abhishek101.core.di

import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    factory { GameDetailViewModel(get(), get(), get(qualifier = named("DefaultScope"))) }
}
