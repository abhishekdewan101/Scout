package com.abhishek101.core.repositories

import com.abhishek101.core.db.Queue
import com.abhishek101.core.utils.DatabaseHelper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

interface QueueRepository {
    fun getAllQueuedGames(): Flow<List<Queue>>
    fun insertGameIntoQueue(slug: String, name: String, coverUrl: String)
    fun updatePositionOfGame(slug: String, newPosition: Long)
    fun isGameInQueue(slug: String): Boolean
}

class QueueRepositoryImpl(databaseHelper: DatabaseHelper) : QueueRepository {
    private val queueQueries = databaseHelper.queueQueries
    override fun getAllQueuedGames(): Flow<List<Queue>> {
        return queueQueries.selectAllGames().asFlow().mapToList()
    }

    override fun insertGameIntoQueue(slug: String, name: String, coverUrl: String) {
        val position = queueQueries.getCurrentNumberOfGamesInQueue().executeAsOne().MAX ?: 0
        queueQueries.insertGameIntoQueue(slug = slug, name = name, coverUrl = coverUrl, position = position + 1)
    }

    override fun updatePositionOfGame(slug: String, newPosition: Long) {
        queueQueries.updatePositionOfGame(position = newPosition, slug = slug)
    }

    override fun isGameInQueue(slug: String): Boolean {
        return queueQueries.isGameQueued(slug).executeAsOne() > 0
    }
}
