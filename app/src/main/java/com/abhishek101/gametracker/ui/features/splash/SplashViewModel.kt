package com.abhishek101.gametracker.ui.features.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    val isAuthenticationValid = mutableStateOf(false)

    fun checkAuthentication() {
        viewModelScope.launch {
            authenticationRepository.getAuthenticationData()
                .onEach {
                    if (it.isNotEmpty()) {
                        isAuthenticationValid.value = true
                    } else {
                        authenticationRepository.authenticateUser()
                    }
                }.collect()
        }
    }
}
