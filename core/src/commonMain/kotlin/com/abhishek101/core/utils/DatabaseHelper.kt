package com.abhishek101.core.utils

import com.abhishek101.core.db.AppDb

class DatabaseHelper(appDb: AppDb) {
    val authenticationQueries = appDb.authenticationQueries
    val platformQueries = appDb.platformQueries
    val genreQueries = appDb.genreQueries
}
