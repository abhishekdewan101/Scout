package com.abhishek101.gametracker

import android.app.Application
import android.content.Context
import com.abhishek101.core.di.initKoin
import com.abhishek101.gametracker.di.appModule
import org.koin.dsl.module
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GameTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogger()
        setupKoin()
    }

    private fun setupKoin() {
        initKoin(
            listOf(
                module { single<Context> { this@GameTrackerApplication } },
                appModule
            )
        )
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Add a crash reporting tree that reports the error to server
        }
    }
}
