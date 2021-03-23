package com.abhishek101.core.remote

import com.abhishek101.core.models.GamePosterRemoteEntity
import com.abhishek101.core.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.takeFrom

interface GameApi {
    suspend fun getGamePostersForQuery(
        query: String,
        accessToken: String
    ): List<GamePosterRemoteEntity>
}

class GameApiImpl(
    private val client: HttpClient,
    private val appConfig: AppConfig
) : GameApi {
    override suspend fun getGamePostersForQuery(
        query: String,
        accessToken: String
    ): List<GamePosterRemoteEntity> {
        return client.post {
            headers {
                append("Client-ID", appConfig.clientId)
                append("Authorization", "Bearer $accessToken")
            }
            url {
                takeFrom("https://api.igdb.com/v4/games")
            }
            body = query
        }
    }
}