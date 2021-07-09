package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRemoteEntity(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("expiresIn") val expiresIn: Long
)

@Serializable
data class TwitchAuthenticationEntity(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long
)

fun TwitchAuthenticationEntity.toAuthenticationRemoteEntity() =
    AuthenticationRemoteEntity(accessToken, expiresIn)
