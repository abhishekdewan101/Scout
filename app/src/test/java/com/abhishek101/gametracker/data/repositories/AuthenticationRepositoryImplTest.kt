package com.abhishek101.gametracker.data.repositories

import com.abhishek101.core.remote.AuthenticationApi
import com.abhishek101.core.repositories.AuthenticationRepositoryImpl
import com.abhishek101.gametracker.data.local.AuthenticationDao
import com.abhishek101.gametracker.data.models.Authentication
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime

class AuthenticationRepositoryImplTest {
    private val authenticationApi: AuthenticationApi = mockk {
        coEvery { authenticateUser() } returns Authentication("accessToken", 1)
    }
    private val authenticationDao: AuthenticationDao = mockk {
        coEvery { insert(any()) } just runs
        coEvery { clearAuthenticationData() } just runs
    }

    private val authenticationRepositoryImpl =
        AuthenticationRepositoryImpl(authenticationApi, authenticationDao)

    @Test
    fun `authenticate user clears authentication data before inserting`() = runBlockingTest {
        authenticationRepositoryImpl.authenticateUser()

        coVerifyOrder {
            authenticationDao.clearAuthenticationData()
            authenticationApi.authenticateUser()
            authenticationDao.insert(
                withArg { auth ->
                    assertThat(auth.authToken).isEqualTo("accessToken")
                    assertThat(auth.expiryDate).isEqualTo(1)
                }
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `get authentication data delegates to the dao`() = runBlockingTest {
        coEvery { authenticationDao.getAuthenticationData(any()) } returns MutableStateFlow(null)

        authenticationRepositoryImpl.getAuthenticationData()

        verify {
            authenticationDao.getAuthenticationData(any())
        }
    }
}
