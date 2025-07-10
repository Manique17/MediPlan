package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_history")
data class MedicationHistoryData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val medName: String,
    val description: String,
    val dosage: String,
    val frequency: String,
    val startDate: String,
    val endDate: String,
    val userId: String,
    val actionType: String, // "TAKEN", "DELETED", "COMPLETED"
    val actionDate: String, // Data quando a ação foi realizada
    val notes: String = "" // Notas adicionais
)