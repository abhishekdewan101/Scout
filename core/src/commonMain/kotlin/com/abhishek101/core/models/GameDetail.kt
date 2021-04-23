package com.abhishek101.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgdbGameDetail(
    @SerialName("slug") val slug: String,
    @SerialName("name") val name: String,
    @SerialName("summary") val summary: String? = null,
    @SerialName("storyline") val storyline: String? = null,
    @SerialName("involved_companies") val involvedCompanies: List<IgdbInvolvedCompany>,
    @SerialName("platforms") val platform: List<IgdbPlatformAbbreviated>,
    @SerialName("screenshots") val screenShots: List<IgdbImage>? = null,
    @SerialName("similar_games") val similarGames: List<IgdbGame>? = null,
    @SerialName("artworks") val artworks: List<IgdbImage>? = null,
    @SerialName("dlcs") val dlc: List<IgdbGame>? = null,
    @SerialName("expansions") val expansions: List<IgdbGame>? = null,
    @SerialName("remasters") val remasters: List<IgdbGame>? = null,
    @SerialName("collection") val collection: IgdbCollection? = null
)

@Serializable
data class IgdbCollection(
    @SerialName("slug") val slug: String,
    @SerialName("name") val name: String,
    @SerialName("games") val games: List<IgdbGame>
)

@Serializable
data class IgdbInvolvedCompany(
    @SerialName("company") val company: IgdbCompany,
    @SerialName("developer") val developer: Boolean,
    @SerialName("publisher") val publisher: Boolean
)

@Serializable
data class IgdbCompany(
    @SerialName("slug") val slug: String,
    @SerialName("name") val name: String,
)