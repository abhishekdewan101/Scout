package com.abhishek101.core.utils

import com.abhishek101.core.viewmodels.gamelist.ListType
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.minus

fun buildGameDetailQuery(slug: String): String {
    return "f slug, name, summary, first_release_date,genres, total_rating,storyline, cover.image_id, videos.*," +
        "screenshots.image_id, platforms.id, platforms.name, platforms.slug," +
        "similar_games.*, similar_games.screenshots.image_id, similar_games.cover.image_id, similar_games.platforms.*," +
        "artworks.image_id, involved_companies.*,involved_companies.company.*," +
        "dlcs.*, dlcs.cover.image_id, dlcs.screenshots.image_id,dlcs.platforms.*," +
        "expansions.*, expansions.cover.image_id, expansions.screenshots.image_id,expansions.platforms.*," +
        "remasters.*, remasters.cover.image_id, remasters.screenshots.image_id,remasters.platforms.*," +
        "collection.*, collection.games.*, collection.games.cover.image_id, collection.games.screenshots.image_id, collection.games.platforms.*;" +
        "w slug = \"$slug\";"
}

fun buildQuery(queryType: ListType, genreFilter: String, platformFilter: String): String {
    val genreAndPreferenceFilter = "$genreFilter & $platformFilter"
    return when (queryType) {
        ListType.SHOWCASE -> buildShowcaseQuery(genreAndPreferenceFilter)
        ListType.TOP_RATED -> buildTopRatedQuery(genreAndPreferenceFilter)
        ListType.COMING_SOON -> buildComingSoonQuery(genreAndPreferenceFilter)
        ListType.MOST_HYPED -> buildMostHypedQuery(genreAndPreferenceFilter)
        ListType.RECENT -> buildRecentGamesQuery(genreAndPreferenceFilter)
        else -> throw IllegalStateException("You can't get list for search type")
    }
}

fun buildSearchQuery(searchTerm: String): String {
    return "f slug, name,cover.image_id, screenshots.image_id, first_release_date, platforms.id, platforms.name, platforms.slug ; search \"$searchTerm\"; l 50;"
}

// FIXME: Perhaps use a builder pattern that can be tested easily.
private fun buildShowcaseQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name,cover.image_id,first_release_date,platforms.id, platforms.name, platforms.slug, screenshots.image_id;" +
        "w rating >= 75 & hypes > 0 & first_release_date < ${Clock.System.now().epochSeconds} " +
        "& first_release_date > ${
        Clock.System.now().minus(6, DateTimeUnit.MONTH, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter; s hypes desc; l 50;"
}

private fun buildTopRatedQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id,first_release_date, platforms.id, platforms.name, platforms.slug, screenshots.image_id;" +
        "w total_rating >= 80 & first_release_date >= ${
        Clock.System.now().minus(1, DateTimeUnit.YEAR, currentSystemDefault()).epochSeconds
        } & $genreAndPreferenceFilter;" +
        "s rating desc;" +
        "l 50;"
}

fun buildComingSoonQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id,first_release_date,platforms.id, platforms.name, platforms.slug, screenshots.image_id;" +
        "w hypes > 0 & first_release_date >= ${Clock.System.now().epochSeconds} & $genreAndPreferenceFilter;" +
        "s first_release_date asc;" +
        "l 50;"
}

fun buildMostHypedQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id,platforms.id,first_release_date, platforms.name, platforms.slug, screenshots.image_id;" +
        "w hypes > 0 & first_release_date >= ${Clock.System.now().epochSeconds} & $genreAndPreferenceFilter;" +
        "s hypes desc;" +
        "l 50;"
}

fun buildRecentGamesQuery(genreAndPreferenceFilter: String): String {
    return "f slug, name, cover.image_id,platforms.id,first_release_date, platforms.name, platforms.slug, screenshots.image_id;" +
        "w total_rating >= 60 & first_release_date <= ${Clock.System.now().epochSeconds}" +
        " & $genreAndPreferenceFilter;" +
        "s first_release_date desc;" +
        "l 50;"
}
