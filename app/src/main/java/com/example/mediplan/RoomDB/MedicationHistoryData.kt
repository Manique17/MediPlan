package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_history")
data class MedicationHistoryData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val medName: String,
    val action: String, // exemplo: "taken", "completed", "removed"
    val actionDate: String,
    val description: String,
    val dosage: String,
    val frequency: String,
    val startDate: String,
    val endDate: String,
    val actionType: String,
    val notes: String
)

