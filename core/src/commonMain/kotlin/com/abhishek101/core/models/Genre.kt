package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreRemoteEntity(
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String
)