package com.abhishek101.core.di

import com.abhishek101.core.viewmodels.authentication.AuthenticationViewModel
import com.abhishek101.core.viewmodels.gamedetails.GameDetailViewModel
import com.abhishek101.core.viewmodels.gamelist.GameListViewModel
import com.abhishek101.core.viewmodels.preferenceselection.PreferenceSelectionViewModel
import com.abhishek101.core.viewmodels.search.SearchViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    factory { GameDetailViewModel(get(), get(), get(qualifier = named("DefaultScope"))) }
    factory {
        AuthenticationViewModel(
            get(),
            get(qualifier = named("MainScope")),
            getWith(AuthenticationViewModel::class.simpleName),
            get()
        )
    }
    factory {
        PreferenceSelectionViewModel(
            get(),
            get(),
            get(),
            get(qualifier = named("DefaultScope"))
        )
    }
    factory {
        GameListViewModel(
            get(),
            get(qualifier = named("DefaultScope"))
        )
    }

    factory {
        SearchViewModel(
            get(),
            get(qualifier = named("DefaultScope"))
        )
    }
}
