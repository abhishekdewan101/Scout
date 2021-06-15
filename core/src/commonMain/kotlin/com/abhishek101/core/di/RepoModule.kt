package com.abhishek101.core.di

import com.abhishek101.core.remote.AuthenticationApiImpl
import com.abhishek101.core.repositories.AuthenticationRepository
import com.abhishek101.core.repositories.AuthenticationRepositoryImpl
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.GameRepositoryImpl
import com.abhishek101.core.repositories.GenreRepository
import com.abhishek101.core.repositories.GenreRepositoryImpl
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.core.repositories.LibraryRepositoryImpl
import com.abhishek101.core.repositories.PlatformRepository
import com.abhishek101.core.repositories.PlatformRepositoryImpl
import com.abhishek101.core.repositories.QueueRepository
import com.abhishek101.core.repositories.QueueRepositoryImpl
import org.koin.dsl.module
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val repoModule = module {
    single<PlatformRepository> {
        PlatformRepositoryImpl(get(), get())
    }
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(AuthenticationApiImpl(get()), get())
    }
    single<GenreRepository> {
        GenreRepositoryImpl(get(), get())
    }
    single<GameRepository> {
        GameRepositoryImpl(get(), get())
    }
    single<LibraryRepository> {
        LibraryRepositoryImpl(get(), get())
    }
    single<QueueRepository> {
        QueueRepositoryImpl(get())
    }
}
