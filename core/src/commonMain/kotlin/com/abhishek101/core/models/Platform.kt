package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgdbPlatform(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("generation") val generation: Long = 0L,
    @SerialName("platform_logo") val logo: IgdbLogo? = null
)

@Serializable
data class IgdbPlatformAbbreviated(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
)

@Serializable
data class IgdbLogo(
    @SerialName("image_id") val imageId: String
)
