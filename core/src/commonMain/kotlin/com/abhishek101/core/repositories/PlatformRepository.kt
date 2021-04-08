package com.abhishek101.core.repositories

import com.abhishek101.core.db.AuthenticationQueries
import com.abhishek101.core.db.Platform
import com.abhishek101.core.db.PlatformQueries
import com.abhishek101.core.remote.PlatformApi
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

interface PlatformRepository {
    fun getCachedPlatforms(): Flow<List<Platform>>
    suspend fun updateCachedPlatforms()
    fun setPlatformAsOwned(platform: Platform, isOwned: Boolean)
    fun getAllOwnedPlatforms(): Flow<List<Platform>>
}

class PlatformRepositoryImpl(
    private val platformApi: PlatformApi,
    private val platformQueries: PlatformQueries,
    private val authenticationQueries: AuthenticationQueries
) : PlatformRepository {

    override fun getCachedPlatforms(): Flow<List<Platform>> {
        return platformQueries.getAllPlatforms().asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }

    override suspend fun updateCachedPlatforms() {
        val timeNow = Clock.System.now().epochSeconds
        val accessToken = authenticationQueries.getAuthenticationData(timeNow).executeAsOne()
        platformApi.getPlatforms(accessToken.accessToken).forEach {
            platformQueries.savePlatform(
                it.id,
                it.slug,
                it.name,
                it.logo.imageId
            )
        }
    }

    override fun setPlatformAsOwned(platform: Platform, isOwned: Boolean) {
        platformQueries.ownPlatform(isOwned, platform.slug)
    }

    override fun getAllOwnedPlatforms(): Flow<List<Platform>> {
        return platformQueries.getUserOwnedPlatforms().asFlow().mapToList()
            .flowOn(Dispatchers.Default)
    }
}
