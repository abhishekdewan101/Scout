package com.abhishek101.gametracker.data.remote

import com.abhishek101.gametracker.data.models.Authentication
import com.abhishek101.gametracker.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.takeFrom
import javax.inject.Inject

interface AuthenticationApi {
    suspend fun authenticateUser(): Authentication
}

class AuthenticationApiImpl @Inject constructor(
    private val client: HttpClient,
    private val appConfig: AppConfig
) : AuthenticationApi {

    override suspend fun authenticateUser() = client.post<Authentication> {
        url {
            takeFrom("https://id.twitch.tv/oauth2/token?client_id=${appConfig.clientId}&client_secret=${appConfig.clientSecret}&grant_type=client_credentials")
        }
    }
}
