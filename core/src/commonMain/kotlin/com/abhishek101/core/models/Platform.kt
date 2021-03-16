package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformRemoteEntity(
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("platform_logo") val logo: LogoRemoteEntity
)

@Serializable
data class LogoRemoteEntity(
    @SerialName("height") val height: Int,
    @SerialName("width") val width: Int,
    @SerialName("image_id") val imageId: String
)
