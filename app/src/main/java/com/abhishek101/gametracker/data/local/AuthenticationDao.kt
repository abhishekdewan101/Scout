package com.abhishek101.gametracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishek101.gametracker.data.models.AuthenticationEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AuthenticationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(authenticationEntity: AuthenticationEntity)

    @Query("SELECT * from authentication where expiryDate <= :now")
    abstract fun getAuthenticationData(now: Long): Flow<AuthenticationEntity?>
}
