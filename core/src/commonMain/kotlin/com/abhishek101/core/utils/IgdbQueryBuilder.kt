package com.abhishek101.core.utils

import com.abhishek101.core.db.Genre
import com.abhishek101.core.db.Platform
import com.abhishek101.core.utils.QueryType.SHOWCASE
import kotlinx.datetime.Clock

enum class QueryType {
    SHOWCASE
}

fun buildQuery(queryType: QueryType, genres: List<Genre>, platforms: List<Platform>) : String {
    return when(queryType) {
     SHOWCASE ->  buildShowcaseQuery(genres, platforms)
    }
}

private fun buildShowcaseQuery(genres: List<Genre>, platforms: List<Platform>): String {
    val ownedPlatformString = "platforms=(${platforms.joinToString(",") { it.id.toString() }})"
    val favoriteGenreStrings = "genres=(${genres.joinToString(",") { it.id.toString() }})"
    return "f name,cover.image_id, screenshots.image_id;w hypes > 0 & first_release_date > ${Clock.System.now().epochSeconds} & $favoriteGenreStrings & $ownedPlatformString; s hypes desc; l 10;"
}
