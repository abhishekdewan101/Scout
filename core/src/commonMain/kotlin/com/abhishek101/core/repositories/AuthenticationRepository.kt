package com.abhishek101.core.repositories

import com.abhishek101.core.db.Authentication
import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.models.toAuthentication
import com.abhishek101.core.remote.AuthenticationApi
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

interface AuthenticationRepository {
    suspend fun authenticateUser()
    suspend fun getAuthenticationData(): Flow<List<Authentication>>
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
    override suspend fun getAuthenticationData(): Flow<List<Authentication>> {
        val timeNow = TimeSource.Monotonic.markNow().elapsedNow().toLong(DurationUnit.SECONDS)
        return authenticationQueries.getAuthenticationData(timeNow).asFlow().mapToList()
    }
}
