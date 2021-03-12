package com.abhishek101.gametracker.ui.features.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    val isAuthenticationValid = mutableStateOf(false)

    fun checkAuthentication() {
        viewModelScope.launch {
            authenticationRepository.getAuthenticationData()
                .collect {
                    if (it != null) {
                        Timber.d("Valid Authentication Found")
                        isAuthenticationValid.value = true
                    } else {
                        Timber.d("Valid Authentication Not Found - Authenticating User")
                        authenticationRepository.authenticateUser()
                    }
                }
        }
    }
}
