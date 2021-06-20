package com.abhishek101.core.viewmodels.authentication

import co.touchlab.kermit.Kermit
import com.abhishek101.core.repositories.AuthenticationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthenticationViewModel constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val mainScope: CoroutineScope,
    private val kermit: Kermit
) {
    private val _viewState = MutableStateFlow(false)

    val viewState: StateFlow<Boolean> = _viewState

    fun checkAuthentication() {
        mainScope.launch {
            authenticationRepository
                .getAuthenticationData()
                .collect {
                    if (it != null) {
                        kermit.d { "Valid Authentication Found" }
                        _viewState.value = true
                    } else {
                        kermit.d { "Valid Authentication Not Found - Authenticating User" }
                        authenticationRepository.authenticateUser()
                    }
                }
        }
    }

    fun checkAuthentication(listener: (Boolean) -> Unit) {
        mainScope.launch {
            authenticationRepository
                .getAuthenticationData()
                .collect {
                    if (it != null) {
                        kermit.d { "Valid Authentication Found" }
                        listener(true)
                    } else {
                        kermit.d { "Valid Authentication Not Found - Authenticating User" }
                        authenticationRepository.authenticateUser()
                    }
                }
        }
    }
}
