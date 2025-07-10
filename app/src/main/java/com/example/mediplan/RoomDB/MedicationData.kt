package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class MedicationData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val medName: String,
    val dosage: String,
    val frequency: String,
    val description: String = "",
    val startDate: String = "",
    val endDate: String = ""
)

