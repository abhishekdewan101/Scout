package com.abhishek101.gametracker.di

import com.abhishek101.gametracker.data.remote.AuthenticationApi
import com.abhishek101.gametracker.data.remote.AuthenticationApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindAuthenticationApi(authenticationApiImpl: AuthenticationApiImpl): AuthenticationApi
}
