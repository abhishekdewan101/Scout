package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamePosterRemoteEntity(
    @SerialName("name") val name: String,
    @SerialName("screenshots") val screenShots: List<Image>,
    @SerialName("cover") val cover: Image
)

@Serializable
data class Image(
    @SerialName("image_id") val imageId: String
)
