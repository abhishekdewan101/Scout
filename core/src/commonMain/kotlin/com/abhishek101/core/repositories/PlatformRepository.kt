package com.abhishek101.core.repositories

import com.abhishek101.core.db.Platform
import com.abhishek101.core.remote.PlatformApi
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface PlatformRepository {
    fun getCachedPlatforms(): Flow<List<Platform>>
    suspend fun updateCachedPlatforms()
    fun setPlatformAsOwned(slug: String, isOwned: Boolean)
    fun getAllOwnedPlatforms(): Flow<List<Platform>>
}

class PlatformRepositoryImpl(
    private val platformApi: PlatformApi,
    private val dbHelper: DatabaseHelper
) : PlatformRepository {

    private val platformQueries = dbHelper.platformQueries

    override fun getCachedPlatforms(): Flow<List<Platform>> {
        return platformQueries.getAllPlatforms().asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }

    override suspend fun updateCachedPlatforms() {
        platformApi.getPlatforms(dbHelper.accessToken).forEach {
            platformQueries.savePlatform(
                it.id,
                it.slug,
                it.name,
                it.generation,
                it.logo.imageId
            )
        }
    }

    override fun setPlatformAsOwned(slug: String, isOwned: Boolean) {
        platformQueries.ownPlatform(isOwned, slug)
    }

    override fun getAllOwnedPlatforms(): Flow<List<Platform>> {
        return platformQueries.getUserOwnedPlatforms().asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }
}
