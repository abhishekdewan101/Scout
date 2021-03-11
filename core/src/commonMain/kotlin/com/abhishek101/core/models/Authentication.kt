package com.abhishek101.core.models

import com.abhishek101.core.db.Authentication
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@Serializable
data class AuthenticationRemoteEntity(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long
)

@ExperimentalTime
fun AuthenticationRemoteEntity.toAuthentication(timeSource: TimeSource = TimeSource.Monotonic): Authentication =
    Authentication(
        accessToken = this.accessToken,
        expiresBy = timeSource.markNow().elapsedNow().toLong(DurationUnit.SECONDS)
    )
