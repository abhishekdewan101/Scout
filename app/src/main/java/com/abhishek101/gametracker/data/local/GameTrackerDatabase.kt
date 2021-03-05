package com.abhishek101.gametracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhishek101.gametracker.data.models.AuthenticationEntity

@Database(
    entities = [
        AuthenticationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GameTrackerDatabase : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao
}
