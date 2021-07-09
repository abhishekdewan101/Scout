package com.abhishek101.core.repositories

import com.abhishek101.core.db.Authentication
import com.abhishek101.core.remote.AuthenticationApi
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

interface AuthenticationRepository {
    suspend fun authenticateUser()
    fun getAuthenticationData(): Flow<Authentication?>
}

@ExperimentalTime
class AuthenticationRepositoryImpl(
    private val authenticationApi: AuthenticationApi,
    dbHelper: DatabaseHelper
) : AuthenticationRepository {

    private val authenticationQueries = dbHelper.authenticationQueries

    override suspend fun authenticateUser() {
        authenticationApi.authenticateUser().apply {
            authenticationQueries.setAuthenticationData(accessToken, expiresIn)
        }
    }

    @ExperimentalTime
    override fun getAuthenticationData(): Flow<Authentication?> {
        val timeNow = Clock.System.now().epochSeconds
        return authenticationQueries.getAuthenticationData(timeNow).asFlow().mapToOneOrNull()
            .flowOn(Dispatchers.Default)
    }
}
