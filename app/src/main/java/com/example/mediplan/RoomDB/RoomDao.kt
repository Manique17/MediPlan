package com.example.mediplan.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    // User operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserData)
    
    @Update
    suspend fun updateUser(user: UserData)
    
    @Delete
    suspend fun deleteUser(user: UserData)
    
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): UserData?
    
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserData?
    
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserData?
    
    // Medication operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationData)
    
    @Update
    suspend fun updateMedication(medication: MedicationData)
    
    @Delete
    suspend fun deleteMedication(medication: MedicationData)
    
    @Query("SELECT * FROM medications WHERE userId = :userId")
    fun getMedicationsByUser(userId: String): Flow<List<MedicationData>>
    
    @Query("SELECT * FROM medications")
    fun getAllMedications(): Flow<List<MedicationData>>
    
    @Query("SELECT * FROM medications WHERE id = :medicationId LIMIT 1")
    suspend fun getMedicationById(medicationId: Int): MedicationData?
    
    @Query("DELETE FROM medications WHERE userId = :userId")
    suspend fun deleteAllMedicationsForUser(userId: String)
}