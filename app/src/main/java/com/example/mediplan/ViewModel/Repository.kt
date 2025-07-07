package com.example.mediplan.ViewModel

import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.UserData
import com.example.mediplan.UserDatabase
import com.example.mediplan.model.Medication

class Repository(private val db: UserDatabase) {

    suspend fun upsertNote(medication: MedicationData){
        db.dao.upsertMedication(medication)
    }

    suspend fun deleteNote(medication: MedicationData){
        db.dao.deleteMedication(medication)
    }

    fun getAllMedication() = db.dao.getAllMedication()

    suspend fun CreateUser(user:UserData){
        db.dao.upsertUser(user)
    }
}