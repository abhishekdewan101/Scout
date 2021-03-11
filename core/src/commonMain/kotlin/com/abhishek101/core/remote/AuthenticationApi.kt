package com.abhishek101.core.remote

import com.abhishek101.core.models.AuthenticationRemoteEntity
import com.abhishek101.core.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.takeFrom

interface AuthenticationApi {
    suspend fun authenticateUser(): AuthenticationRemoteEntity
}

class AuthenticationApiImpl(
    private val client: HttpClient,
    private val appConfig: AppConfig
) : AuthenticationApi {

    override suspend fun authenticateUser() = client.post<AuthenticationRemoteEntity> {
        url {
            takeFrom("https://id.twitch.tv/oauth2/token?client_id=${appConfig.clientId}&client_secret=${appConfig.clientSecret}&grant_type=client_credentials")
        }
    }
}
