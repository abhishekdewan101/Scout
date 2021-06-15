package com.abhishek101.core.utils

import com.abhishek101.core.db.AppDb
import kotlinx.datetime.Clock

class DatabaseHelper(appDb: AppDb) {
    val queueQueries = appDb.queueQueries
    val authenticationQueries = appDb.authenticationQueries
    val platformQueries = appDb.platformQueries
    val genreQueries = appDb.genreQueries
    val libraryGameQueries = appDb.libraryQueries

    val accessToken by lazy {
        val timeNow = Clock.System.now().epochSeconds
        val authentication =
            authenticationQueries.getAuthenticationData(timeNow).executeAsOneOrNull()
        authentication?.accessToken
            ?: throw RuntimeException("Accessing authentication for unauthenticated user")
    }

    val genreFilter by lazy {
        "genres=( ${
        genreQueries.getAllFavoriteGenres()
            .executeAsList()
            .joinToString(",") { it.id.toString() }
        } )"
    }

    val platformFilter by lazy {
        "platforms=(${
        platformQueries.getUserOwnedPlatforms()
            .executeAsList()
            .joinToString(",") { it.id.toString() }
        })"
    }
}
