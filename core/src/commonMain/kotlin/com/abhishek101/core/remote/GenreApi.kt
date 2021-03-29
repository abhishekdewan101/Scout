package com.abhishek101.core.remote

import com.abhishek101.core.models.GenreRemoteEntity
import com.abhishek101.core.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.takeFrom

interface GenreApi {
    suspend fun getGenres(accessToken: String): List<GenreRemoteEntity>
}

class GenreApiImpl(
    private val client: HttpClient,
    private val appConfig: AppConfig
) : GenreApi {
    override suspend fun getGenres(accessToken: String): List<GenreRemoteEntity> {
        return client.post {
            headers {
                append("Client-ID", appConfig.clientId)
                append("Authorization", "Bearer $accessToken")
            }
            url {
                takeFrom("https://api.igdb.com/v4/genres")
            }
            body = "f name,slug;l 50;"
        }
    }
}
