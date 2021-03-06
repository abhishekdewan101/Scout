package com.abhishek101.gametracker.di

import android.content.Context
import androidx.room.Room
import com.abhishek101.gametracker.data.local.GameTrackerDatabase
import com.abhishek101.gametracker.utils.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GameTrackerDatabase::class.java, "data.db").build()

    @Provides
    fun provideKtorClient() = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    @Provides
    fun provideAppConfig() = AppConfig()
}
