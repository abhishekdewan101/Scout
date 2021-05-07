package com.abhishek101.core.remote

import com.abhishek101.core.models.IgdbGame
import com.abhishek101.core.models.IgdbGameDetail
import com.abhishek101.core.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.takeFrom

interface GameApi {
    suspend fun getGamePostersForQuery(query: String, accessToken: String): List<IgdbGame>

    suspend fun getGameDetailsForQuery(query: String, accessToken: String): IgdbGameDetail

    suspend fun searchGamesForQuery(query: String, accessToken: String): List<IgdbGame>
}

class GameApiImpl(
    private val client: HttpClient,
    private val appConfig: AppConfig
) : GameApi {
    override suspend fun getGamePostersForQuery(
        query: String,
        accessToken: String
    ): List<IgdbGame> {
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

    override suspend fun getGameDetailsForQuery(
        query: String,
        accessToken: String
    ): IgdbGameDetail {
        return client.post<List<IgdbGameDetail>> {
            headers {
                append("Client-ID", appConfig.clientId)
                append("Authorization", "Bearer $accessToken")
            }
            url {
                takeFrom("https://api.igdb.com/v4/games")
            }
            body = query
        }[0]
    }

    override suspend fun searchGamesForQuery(query: String, accessToken: String): List<IgdbGame> {
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
