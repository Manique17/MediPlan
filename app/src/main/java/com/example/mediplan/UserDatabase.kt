package com.example.mediplan

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.RoomDao

@Database(
    entities = [MedicationData::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract val dao : RoomDao

}