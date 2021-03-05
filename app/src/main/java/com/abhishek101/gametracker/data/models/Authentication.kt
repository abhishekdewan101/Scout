package com.abhishek101.gametracker.data.models

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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

data class Authentication(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Long
)

@OptIn(ExperimentalTime::class)
fun Authentication.toDbEntity(timeSource: TimeSource = TimeSource.Monotonic): AuthenticationEntity =
    AuthenticationEntity(
        authToken = this.accessToken,
        expiryDate = timeSource.markNow().elapsedNow().toLong(TimeUnit.SECONDS) + this.expiresIn,
    )
