package com.abhishek101.gamescout

import android.app.Application
import android.content.Context
import com.abhishek101.core.di.initKoin
import com.abhishek101.gamescout.di.appModule
import org.koin.dsl.module
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogger()
        setupKoin()
    }

    private fun setupKoin() {
        initKoin(
            listOf(
                module { single<Context> { this@GSApplication } },
                appModule
            )
        )
    }

    private fun setupLogger() {
        if (com.abhishek101.gamescout.BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Add a crash reporting tree that reports the error to server
        }
    }
}
