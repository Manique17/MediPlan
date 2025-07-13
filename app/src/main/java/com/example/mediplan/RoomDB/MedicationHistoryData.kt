package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "medication_history") // Nome da tabela na base de dados
data class MedicationHistoryData(

    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID do registo


    val userId: String, // Quem é o utilizador


    val medName: String, // Nome do remédio


    val action: String, // Ação feita (ex: "tomado")


    val actionDate: String, // Data da ação


    val description: String, // Descrição do remédio


    val dosage: String, // Dose do remédio


    val frequency: String, // Frequência do remédio


    val startDate: String, // Início do tratamento


    val endDate: String, // Fim do tratamento


    val actionType: String, // Tipo específico da ação


    val notes: String // Notas extra
)


