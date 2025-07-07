package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    val Name: String,
    val Email: String,
    val Password: String,
    val Birthday: String = "Não especificado",
    @PrimaryKey(autoGenerate = true)
    val UserId : Int = 0
)
