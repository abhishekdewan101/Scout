package com.abhishek101.core.remote

import com.abhishek101.core.models.AuthenticationRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.takeFrom

interface AuthenticationApi {
    suspend fun authenticateUser(): AuthenticationRemoteEntity
}

class AuthenticationApiImpl(
    private val client: HttpClient
) : AuthenticationApi {

    override suspend fun authenticateUser() = client.get<AuthenticationRemoteEntity> {
        url {
            takeFrom("https://px058nbguc.execute-api.us-east-1.amazonaws.com/default/authentication")
        }
    }
}
