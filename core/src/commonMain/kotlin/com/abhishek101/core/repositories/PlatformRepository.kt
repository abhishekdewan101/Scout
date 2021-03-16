package com.abhishek101.core.repositories

import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.db.FavouritePlatformsQueries
import com.abhishek101.core.db.Platforms
import com.abhishek101.core.remote.PlatformApi
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface PlatformRepository {
    suspend fun getPlatforms(): Flow<List<Platforms>>
    fun updateFavoritePlatform(platform: Platforms, isFavorite: Boolean)
    fun getAllFavoritePlatforms(): Flow<List<Platforms>>
}

class PlatformRepositoryImpl(
    private val platformApi: PlatformApi,
    private val favouritePlatformsQueries: FavouritePlatformsQueries,
    private val authenticationQueries: AuthenticationQueries
) : PlatformRepository {

    override suspend fun getPlatforms(): Flow<List<Platforms>> {
        val cachedPlatforms = favouritePlatformsQueries.getAllPlatforms().executeAsList()

        if (cachedPlatforms.isEmpty()) {
            val timeNow = Clock.System.now().epochSeconds
            val accessToken = authenticationQueries.getAuthenticationData(timeNow).executeAsOne()
            platformApi.getPlatforms(accessToken.accessToken).forEach {
                favouritePlatformsQueries.insertPlatform(
                    it.slug,
                    it.name,
                    it.logo.height.toLong(),
                    it.logo.width.toLong(),
                    it.logo.imageId
                )
            }
            return favouritePlatformsQueries.getAllPlatforms().asFlow().mapToList()
                .flowOn(Dispatchers.Default)
        } else {
            return flowOf(cachedPlatforms).flowOn(Dispatchers.Default)
        }
    }

    override fun updateFavoritePlatform(platform: Platforms, isFavorite: Boolean) {
        favouritePlatformsQueries.updateFavoritePlatform(isFavorite, platform.slug)
    }

    override fun getAllFavoritePlatforms(): Flow<List<Platforms>> {
        return favouritePlatformsQueries.getAllFavoritePlatforms().asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }
}
