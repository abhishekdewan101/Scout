package com.abhishek101.core.utils

import com.abhishek101.core.repositories.ListType
import com.abhishek101.core.repositories.ListType.COMING_SOON
import com.abhishek101.core.repositories.ListType.MOST_HYPED
import com.abhishek101.core.repositories.ListType.RECENT
import com.abhishek101.core.repositories.ListType.SHOWCASE
import com.abhishek101.core.repositories.ListType.TOP_RATED
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.minus

fun buildQuery(queryType: ListType, genreFilter: String, platformFilter: String): String {
    val genreAndPreferenceFilter = "$genreFilter & $platformFilter"
    return when (queryType) {
        SHOWCASE -> buildShowcaseQuery(genreAndPreferenceFilter)
        TOP_RATED -> buildTopRatedQuery(genreAndPreferenceFilter)
        COMING_SOON -> buildComingSoonQuery(genreAndPreferenceFilter)
        MOST_HYPED -> buildMostHypedQuery(genreAndPreferenceFilter)
        RECENT -> buildRecentGamesQuery(genreAndPreferenceFilter)
    }
}

// FIXME: Perhaps use a builder pattern that can be tested easily.
private fun buildShowcaseQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name,cover.image_id, screenshots.image_id;w rating >= 75 & hypes > 0 & first_release_date < ${Clock.System.now().epochSeconds} " +
        "& first_release_date > ${
        Clock.System.now().minus(6, DateTimeUnit.MONTH, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter; s hypes desc; l 50;"
}

private fun buildTopRatedQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w total_rating >= 80 & first_release_date >= ${
        Clock.System.now().minus(1, DateTimeUnit.YEAR, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter;" +
        "s rating desc;" +
        "l 50;"
}

fun buildComingSoonQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w hypes > 0 & first_release_date >= ${Clock.System.now().epochSeconds} & $genreAndPreferenceFilter;" +
        "s first_release_date asc;" +
        "l 50;"
}

fun buildMostHypedQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w hypes > 0 & first_release_date >= ${Clock.System.now().epochSeconds} & $genreAndPreferenceFilter;" +
        "s hypes desc;" +
        "l 50;"
}

fun buildRecentGamesQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id, screenshots.image_id;" +
        "w total_rating >= 60 & first_release_date <= ${Clock.System.now().epochSeconds}" +
        " & $genreAndPreferenceFilter;" +
        "s first_release_date desc;" +
        "l 50;"
}
