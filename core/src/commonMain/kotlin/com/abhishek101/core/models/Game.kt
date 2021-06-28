package com.abhishek101.core.models

import com.abhishek101.core.utils.buildImageString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgdbGame(
    @SerialName("slug") val slug: String,
    @SerialName("name") val name: String,
    @SerialName("screenshots") val screenShots: List<IgdbImage>? = null,
    @SerialName("first_release_date") val firstReleaseDate: Long? = null,
    @SerialName("platforms") val platform: List<IgdbPlatformAbbreviated>? = null,
    @SerialName("cover") val cover: IgdbImage? = null
)

@Serializable
data class IgdbImage(
    @SerialName("image_id") val imageId: String
) {
    val qualifiedUrl: String
        get() = buildImageString(imageId)
}
