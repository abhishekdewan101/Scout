package com.abhishek101.gametracker

import android.app.Application
import timber.log.Timber

class GameTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupLogger()
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Add a crash reporting tree that reports the error to server
        }
    }
}
