package com.example.mediplan

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mediplan.RoomDB.Converters
import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.MedicationHistoryData
import com.example.mediplan.RoomDB.RoomDao
import com.example.mediplan.RoomDB.UserData

@Database(
    entities = [UserData::class, MedicationData::class, MedicationHistoryData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: RoomDao
    
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "mediplan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}