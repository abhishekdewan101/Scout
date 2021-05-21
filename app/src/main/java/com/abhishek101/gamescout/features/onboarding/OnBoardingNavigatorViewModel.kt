package com.abhishek101.gamescout.features.onboarding

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

const val onBoardingCompleteKey = "OnBoardingComplete"

class OnBoardingNavigatorViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    fun isOnBoardingComplete() = sharedPreferences.getBoolean(onBoardingCompleteKey, false)

    fun updateOnBoardingComplete() {
        sharedPreferences.edit().putBoolean(onBoardingCompleteKey, true).apply()
    }
}
