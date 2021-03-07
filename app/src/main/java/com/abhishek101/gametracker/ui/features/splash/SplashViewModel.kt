package com.abhishek101.gametracker.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.gametracker.data.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    private val _isAuthenticationValid = MutableStateFlow(false)

    val isAuthenticationValid = _isAuthenticationValid.asStateFlow()

    fun checkAuthentication() {
        viewModelScope.launch {
            authenticationRepository.getAuthenticationData()
                .onEach {
                    if (it != null) {
                        _isAuthenticationValid.value = true
                    } else {
                        authenticationRepository.authenticateUser()
                    }
                }.collect()
        }
    }
}