package com.abhishek101.gametracker.ui.features.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.gametracker.data.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    val isAuthenticationValid = mutableStateOf(false)

    fun checkAuthentication() {
        viewModelScope.launch {
            authenticationRepository.getAuthenticationData()
                .onEach {
                    if (it != null) {
                        isAuthenticationValid.value = true
                    } else {
                        authenticationRepository.authenticateUser()
                    }
                }.collect()
        }
    }
}
