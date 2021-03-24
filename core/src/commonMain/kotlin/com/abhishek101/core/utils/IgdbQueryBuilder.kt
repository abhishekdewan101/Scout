package com.abhishek101.core.utils

import com.abhishek101.core.db.Genre
import com.abhishek101.core.db.Platform
import com.abhishek101.core.utils.QueryType.SHOWCASE
import com.abhishek101.core.utils.QueryType.TOP_RATED_LAST_YEAR
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.minus

enum class QueryType {
    SHOWCASE,
    TOP_RATED_LAST_YEAR
}

fun buildQuery(queryType: QueryType, genres: List<Genre>, platforms: List<Platform>): String {
    val ownedPlatformString = "platforms=(${platforms.joinToString(",") { it.id.toString() }})"
    val favoriteGenreStrings = "genres=(${genres.joinToString(",") { it.id.toString() }})"
    val genreAndPreferenceFilter = "$ownedPlatformString & $favoriteGenreStrings"
    return when (queryType) {
        SHOWCASE -> buildShowcaseQuery(genreAndPreferenceFilter)
        TOP_RATED_LAST_YEAR -> buildTopRatedQuery(genreAndPreferenceFilter)
    }
}

private fun buildTopRatedQuery(genreAndPreferenceFilter: String): String {
    return "f name, cover.image_id, screenshots.image_id;" +
        "w rating >= 70 & first_release_date >= ${
            Clock.System.now().minus(1, DateTimeUnit.YEAR, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter;" +
        "s rating desc;" +
        "l 8;"
}

private fun buildShowcaseQuery(genreAndPreferenceFilter: String): String {
    return "f name,cover.image_id, screenshots.image_id;w hypes > 0 & first_release_date > ${Clock.System.now().epochSeconds} & $genreAndPreferenceFilter; s hypes desc; l 10;"
}
