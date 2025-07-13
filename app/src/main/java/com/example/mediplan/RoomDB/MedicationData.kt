package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "medications") // Define o nome da tabela no banco de dados
data class MedicationData(

    @PrimaryKey(autoGenerate = true) // Torna 'id' a chave principal e auto-incrementável
    val id: Int = 0, // O ID da medicação


    val userId: String, // Identificador do utilizador


    val medName: String, // Nome do medicamento


    val dosage: String, // Quantidade a ser tomada


    val frequency: String, // Frequência de uso


    val description: String = "", // Descrição adicional (opcional)


    val startDate: String = "", // Data de início (opciona)


    val endDate: String = "" // Data de término (opciona)
)


