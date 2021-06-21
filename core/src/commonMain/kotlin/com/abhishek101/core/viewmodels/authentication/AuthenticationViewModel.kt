package com.abhishek101.core.viewmodels.authentication

import co.touchlab.kermit.Kermit
import com.abhishek101.core.repositories.AuthenticationRepository
import com.abhishek101.core.utils.KeyValueStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class AuthenticationState(
    val isAuthenticated: Boolean,
    val isOnboardingCompleted: Boolean
)

const val OnboardingCompleteKey = "OnboardingCompleteKey"

class AuthenticationViewModel constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val mainScope: CoroutineScope,
    private val kermit: Kermit,
    private val keyValueStore: KeyValueStore,
) {
    private val _viewState = MutableStateFlow(
        AuthenticationState(
            isAuthenticated = false,
            isOnboardingCompleted = false
        )
    )

    val viewState: StateFlow<AuthenticationState> = _viewState

    fun checkAuthentication() {
        mainScope.launch {
            authenticationRepository
                .getAuthenticationData()
                .collect {
                    if (it != null) {
                        kermit.d { "Valid Authentication Found" }
                        _viewState.value = buildViewState(
                            hasValidAuthentication = true,
                            hasCompletedOnboarding = keyValueStore.getBoolean(OnboardingCompleteKey)
                        )
                    } else {
                        kermit.d { "Valid Authentication Not Found - Authenticating User" }
                        authenticationRepository.authenticateUser()
                    }
                }
        }
    }

    fun checkAuthentication(listener: (AuthenticationState) -> Unit) {
        mainScope.launch {
            authenticationRepository
                .getAuthenticationData()
                .collect {
                    if (it != null) {
                        kermit.d { "Valid Authentication Found" }
                        listener(
                            buildViewState(
                                hasValidAuthentication = true,
                                hasCompletedOnboarding = keyValueStore.getBoolean(
                                    OnboardingCompleteKey
                                )
                            )
                        )
                    } else {
                        kermit.d { "Valid Authentication Not Found - Authenticating User" }
                        authenticationRepository.authenticateUser()
                    }
                }
        }
    }

    private fun buildViewState(
        hasValidAuthentication: Boolean,
        hasCompletedOnboarding: Boolean
    ) = AuthenticationState(
        isAuthenticated = hasValidAuthentication,
        isOnboardingCompleted = hasCompletedOnboarding
    )
}
