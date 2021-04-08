package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgdbPlatform(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("platform_logo") val logo: IgdbLogo
)

@Serializable
data class IgdbLogo(
    @SerialName("image_id") val imageId: String
)
