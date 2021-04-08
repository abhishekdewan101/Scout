package com.abhishek101.core.remote

import com.abhishek101.core.models.IgdbPlatform
import com.abhishek101.core.utils.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.takeFrom

interface PlatformApi {
    suspend fun getPlatforms(accessToken: String): List<IgdbPlatform>
}

class PlatformApiImpl(
    private val client: HttpClient,
    private val appConfig: AppConfig
) : PlatformApi {

    override suspend fun getPlatforms(accessToken: String): List<IgdbPlatform> {
        return client.post {
            headers {
                append("Client-ID", appConfig.clientId)
                append("Authorization", "Bearer $accessToken")
            }
            url {
                takeFrom("https://api.igdb.com/v4/platforms")
            }
            body =
                "f name,slug,platform_logo.*; where generation >=4 & category = 1; sort generation desc; l 17;"
        }
    }
}
