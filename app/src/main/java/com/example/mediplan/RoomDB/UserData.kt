package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserData(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val dateOfBirth: String // Using String instead of LocalDate for Room compatibility
)