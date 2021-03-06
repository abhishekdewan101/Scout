package com.abhishek101.gametracker.data.models

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@Entity(tableName = "authentication")

@Immutable
data class AuthenticationEntity(
    @PrimaryKey
    @ColumnInfo(name = "authToken")
    val authToken: String,
    @ColumnInfo(name = "expiryDate")
    val expiryDate: Long,
)

@Serializable
data class Authentication(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long
)

@OptIn(ExperimentalTime::class)
fun Authentication.toDbEntity(timeSource: TimeSource = TimeSource.Monotonic): AuthenticationEntity =
    AuthenticationEntity(
        authToken = this.accessToken,
        expiryDate = timeSource.markNow().elapsedNow().toLong(TimeUnit.SECONDS) + this.expiresIn,
    )
