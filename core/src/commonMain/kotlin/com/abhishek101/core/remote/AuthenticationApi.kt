package com.abhishek101.core.remote

import com.abhishek101.core.BuildKonfig
import com.abhishek101.core.models.AuthenticationRemoteEntity
import com.abhishek101.core.models.TwitchAuthenticationEntity
import com.abhishek101.core.models.toAuthenticationRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.takeFrom

interface AuthenticationApi {
    suspend fun authenticateUser(): AuthenticationRemoteEntity
}

class TwitchAuthenticationApiImpl(private val client: HttpClient) : AuthenticationApi {
    override suspend fun authenticateUser() = client.post<TwitchAuthenticationEntity> {
        url {
            takeFrom("https://id.twitch.tv/oauth2/token?client_id=${BuildKonfig.ClientId}&client_secret=${BuildKonfig.ClientSecret}&grant_type=client_credentials")
        }
    }.toAuthenticationRemoteEntity()
}

class AuthenticationApiImpl(
    private val client: HttpClient
) : AuthenticationApi {

    override suspend fun authenticateUser() = client.get<AuthenticationRemoteEntity> {
        url {
            takeFrom(BuildKonfig.ClientAuthenticationUrl!!)
        }
    }
}
