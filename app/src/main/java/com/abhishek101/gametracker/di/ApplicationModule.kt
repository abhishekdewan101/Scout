package com.abhishek101.gametracker.di

import android.content.Context
import androidx.room.Room
import com.abhishek101.gametracker.data.local.GameTrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GameTrackerDatabase::class.java, "data.db").build()
}
