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

class AuthenticationApiImpl(
    private val client: HttpClient
) : AuthenticationApi {

    override suspend fun authenticateUser(): AuthenticationRemoteEntity {
        return if (BuildKonfig.UseTwitchAuthentication) {
            doTwitchAuthentication()
        } else {
            doApiAuthentication()
        }
    }

    private suspend fun doApiAuthentication(): AuthenticationRemoteEntity =
        client.get {
            url {
                takeFrom(BuildKonfig.ClientAuthenticationUrl)
            }
        }

    private suspend fun doTwitchAuthentication() = client.post<TwitchAuthenticationEntity> {
        url {
            takeFrom(BuildKonfig.ClientAuthenticationUrl)
        }
    }.toAuthenticationRemoteEntity()
}
