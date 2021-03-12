package com.abhishek101.core.repositories

import com.abhishek101.core.db.Authentication
import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.models.toAuthentication
import com.abhishek101.core.remote.AuthenticationApi
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

interface AuthenticationRepository {
    suspend fun authenticateUser(forceClear: Boolean)
    fun getAuthenticationData(): Flow<List<Authentication>>
}

@ExperimentalTime
class AuthenticationRepositoryImpl(
    private val authenticationApi: AuthenticationApi,
    private val authenticationQueries: AuthenticationQueries
) : AuthenticationRepository {

    override suspend fun authenticateUser(forceClear: Boolean) {
        authenticationApi.authenticateUser().toAuthentication().apply {
            if (forceClear) authenticationQueries.clearAuthenticationData()
            authenticationQueries.setAuthenticationData(accessToken, expiresBy)
        }
    }

    @ExperimentalTime
    override fun getAuthenticationData(): Flow<List<Authentication>> {
        val timeNow = Clock.System.now().epochSeconds
        return authenticationQueries.getAuthenticationData(timeNow).asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }
}
