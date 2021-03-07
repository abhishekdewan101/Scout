package com.abhishek101.gametracker.di

import com.abhishek101.gametracker.data.repositories.AuthenticationRepository
import com.abhishek101.gametracker.data.repositories.AuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}
