package com.abhishek101.gametracker.data.repositories

import com.abhishek101.gametracker.data.local.AuthenticationDao
import com.abhishek101.gametracker.data.models.AuthenticationEntity
import com.abhishek101.gametracker.data.models.toDbEntity
import com.abhishek101.gametracker.data.remote.AuthenticationApi
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

interface AuthenticationRepository {
    suspend fun authenticateUser()
    suspend fun getAuthenticationData(): Flow<AuthenticationEntity?>
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val authenticationDao: AuthenticationDao
) : AuthenticationRepository {

    override suspend fun authenticateUser() {
        authenticationDao.clearAuthenticationData()
        authenticationDao.insert(authenticationApi.authenticateUser().toDbEntity())
    }

    @ExperimentalTime
    override suspend fun getAuthenticationData(): Flow<AuthenticationEntity?> {
        val timeNow = TimeSource.Monotonic.markNow().elapsedNow().toLong(TimeUnit.SECONDS)
        return authenticationDao.getAuthenticationData(timeNow)
    }
}
