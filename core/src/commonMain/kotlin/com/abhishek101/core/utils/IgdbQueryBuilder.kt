package com.abhishek101.core.utils

import com.abhishek101.core.db.Genre
import com.abhishek101.core.db.Platform
import com.abhishek101.core.utils.QueryType.COMING_SOON
import com.abhishek101.core.utils.QueryType.SHOWCASE
import com.abhishek101.core.utils.QueryType.TOP_RATED_LAST_YEAR
import com.abhishek101.core.utils.QueryType.TOP_RATED_SINGLE_PLAYER
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.minus

enum class QueryType {
    SHOWCASE,
    TOP_RATED_LAST_YEAR,
    COMING_SOON,
    TOP_RATED_SINGLE_PLAYER,
}

fun buildQuery(queryType: QueryType, genres: List<Genre>, platforms: List<Platform>): String {
    val ownedPlatformString = "platforms=(${platforms.joinToString(",") { it.id.toString() }})"
    val favoriteGenreStrings = "genres=(${genres.joinToString(",") { it.id.toString() }})"
    val genreAndPreferenceFilter = "$ownedPlatformString & $favoriteGenreStrings"
    return when (queryType) {
        SHOWCASE -> buildShowcaseQuery(genreAndPreferenceFilter)
        TOP_RATED_LAST_YEAR -> buildTopRatedQuery(genreAndPreferenceFilter)
        COMING_SOON -> buildComingSoonQuery(genreAndPreferenceFilter)
        TOP_RATED_SINGLE_PLAYER -> buildTopRatedSinglePLayer(genreAndPreferenceFilter)
    }
}

fun buildTopRatedSinglePLayer(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w category=(0) & game_modes=(1) & rating >= 80 & $genreAndPreferenceFilter;" +
        "s rating desc;" +
        "l 9;"
}

fun buildComingSoonQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w hypes > 0 & first_release_date >= ${Clock.System.now().epochSeconds} & $genreAndPreferenceFilter;" +
        "s first_release_date asc;" +
        "l 9;"
}

private fun buildTopRatedQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w rating >= 70 & first_release_date >= ${
        Clock.System.now().minus(1, DateTimeUnit.YEAR, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter;" +
        "s rating desc;" +
        "l 9;"
}

private fun buildShowcaseQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name,cover.image_id, screenshots.image_id;w rating >= 75 & hypes > 0 & first_release_date < ${Clock.System.now().epochSeconds} " +
        "& first_release_date > ${
        Clock.System.now().minus(6, DateTimeUnit.MONTH, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter; s hypes desc; l 9;"
}
