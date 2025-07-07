package com.example.mediplan.ViewModel

import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.RoomDao
import com.example.mediplan.RoomDB.UserData
import kotlinx.coroutines.flow.Flow

class Repository(private val dao: RoomDao) {
    
    // User operations
    suspend fun insertUser(user: UserData) {
        dao.insertUser(user)
    }
    
    suspend fun updateUser(user: UserData) {
        dao.updateUser(user)
    }
    
    suspend fun deleteUser(user: UserData) {
        dao.deleteUser(user)
    }
    
    suspend fun loginUser(email: String, password: String): UserData? {
        return dao.loginUser(email, password)
    }
    
    suspend fun getUserByEmail(email: String): UserData? {
        return dao.getUserByEmail(email)
    }
    
    suspend fun getUserById(userId: String): UserData? {
        return dao.getUserById(userId)
    }
    
    // Medication operations
    suspend fun insertMedication(medication: MedicationData) {
        dao.insertMedication(medication)
    }
    
    suspend fun updateMedication(medication: MedicationData) {
        dao.updateMedication(medication)
    }
    
    suspend fun deleteMedication(medication: MedicationData) {
        dao.deleteMedication(medication)
    }
    
    fun getMedicationsByUser(userId: String): Flow<List<MedicationData>> {
        return dao.getMedicationsByUser(userId)
    }
    
    fun getAllMedications(): Flow<List<MedicationData>> {
        return dao.getAllMedications()
    }
    
    suspend fun getMedicationById(medicationId: Int): MedicationData? {
        return dao.getMedicationById(medicationId)
    }
    
    suspend fun deleteAllMedicationsForUser(userId: String) {
        dao.deleteAllMedicationsForUser(userId)
    }
}