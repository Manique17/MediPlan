package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class MedicationData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val medName: String,
    val description: String,
    val dosage: String,
    val frequency: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val userId: String = "" // Foreign key to link with user
)