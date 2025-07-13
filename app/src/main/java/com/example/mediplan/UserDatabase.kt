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

// base de dados do Room para o aplicativo Mediplan
@Database(
    entities = [UserData::class, MedicationData::class, MedicationHistoryData::class],
    version = 3,
    exportSchema = false
)
// A anotação @Database informa ao Room que esta é uma base de dados
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: RoomDao

    // A propriedade 'dao' é o Data Access Object (DAO) que permite interagir com a base de dados
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // Método para obter a instância do banco de dados
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "mediplan_database"
                )
                    .fallbackToDestructiveMigration() // For development - removes this in production
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}