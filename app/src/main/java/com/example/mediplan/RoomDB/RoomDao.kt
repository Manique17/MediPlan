package com.example.mediplan.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.mediplan.model.Medication
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Upsert
    suspend fun upsertMedication(medication: MedicationData)
    @Delete
    suspend fun deleteMedication(medication: MedicationData)

    @Query("SELECT * FROM MedicationData")
    fun getAllMedication(): Flow<List<MedicationData>>

    @Upsert
    suspend fun upsertUser(user: UserData)

}