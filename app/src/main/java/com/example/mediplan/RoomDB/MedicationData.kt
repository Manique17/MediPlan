package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicationData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val medName:String,
    val description:String,
    val dosage:String
)
