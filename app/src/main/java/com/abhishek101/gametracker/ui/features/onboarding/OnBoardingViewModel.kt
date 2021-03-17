package com.abhishek101.gametracker.ui.features.onboarding

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel

const val onBoardingPrefKey = "onBoardingPrefKey"
const val onBoardingCompleteKey = "OnBoardingComplete"

class OnBoardingViewModel(context: Context) : ViewModel() {
    private val prefs = context.getSharedPreferences(onBoardingPrefKey, MODE_PRIVATE)

    fun getOnBoardingComplete(): Boolean {
        return prefs.getBoolean(onBoardingCompleteKey, false)
    }

    fun updateOnBoardingCompleted() {
        prefs.edit().putBoolean(onBoardingCompleteKey, true).apply()
    }
}