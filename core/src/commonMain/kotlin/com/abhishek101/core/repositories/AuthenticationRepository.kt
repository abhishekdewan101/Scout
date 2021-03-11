package com.abhishek101.core.repositories

import com.abhishek101.core.db.Authentication
import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.models.toAuthentication
import com.abhishek101.core.remote.AuthenticationApi
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

interface AuthenticationRepository {
    suspend fun authenticateUser()
    suspend fun getAuthenticationData(): Flow<Query<Authentication>>
}

@ExperimentalTime
class AuthenticationRepositoryImpl(
    private val authenticationApi: AuthenticationApi,
    private val authenticationQueries: AuthenticationQueries
) : AuthenticationRepository {

    override suspend fun authenticateUser() {
        authenticationQueries.clearAuthenticationData()
        authenticationApi.authenticateUser().toAuthentication().apply {
            authenticationQueries.setAuthenticationData(accessToken, expiresBy)
        }
    }

    @ExperimentalTime
    override suspend fun getAuthenticationData(): Flow<Query<Authentication>> {
        val timeNow = Clock.System.now().epochSeconds
        return authenticationQueries.getAuthenticationData(timeNow).asFlow()
    }
}
