package com.abhishek101.gamescout.features.onboarding

import android.content.SharedPreferences

const val onBoardingCompleteKey = "OnBoardingComplete"

class MainNavigatorViewModel(
    private val sharedPreferences: SharedPreferences
) {

    fun isOnBoardingComplete() = sharedPreferences.getBoolean(onBoardingCompleteKey, false)

    fun updateOnBoardingComplete() {
        sharedPreferences.edit().putBoolean(onBoardingCompleteKey, true).apply()
    }
}
