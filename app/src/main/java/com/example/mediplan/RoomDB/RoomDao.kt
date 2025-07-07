package com.example.mediplan.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Upsert
    suspend fun upsertUser(User: UserData)
    @Delete
    suspend fun deleteUser(User: UserData)

    @Query("SELECT * FROM UserData")
    fun getAllNotes(): Flow<List<UserData>>

}