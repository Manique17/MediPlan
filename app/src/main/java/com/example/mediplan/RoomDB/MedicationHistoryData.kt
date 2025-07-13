package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Guarda o histórico de como uma medicação foi usada.
 * O Room cria uma tabela chamada "medication_history" com base nisto.
 */
@Entity(tableName = "medication_history") // Nome da tabela na base de dados
data class MedicationHistoryData(
    /**
     * ID único para este registo no histórico (o Room gera isto).
     */
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID do registo

    /**
     * ID do utilizador dono deste histórico.
     */
    val userId: String, // Quem é o utilizador

    /**
     * Nome do medicamento deste registo.
     */
    val medName: String, // Nome do remédio

    /**
     * O que aconteceu com o medicamento (ex: "tomado", "removido").
     */
    val action: String, // Ação feita (ex: "tomado")

    /**
     * Data em que a ação aconteceu (em texto, ex: "2023-10-27").
     */
    val actionDate: String, // Data da ação

    /**
     * Descrição do medicamento na altura.
     */
    val description: String, // Descrição do remédio

    /**
     * Dosagem do medicamento na altura.
     */
    val dosage: String, // Dose do remédio

    /**
     * Frequência do medicamento na altura.
     */
    val frequency: String, // Frequência do remédio

    /**
     * Data de início do tratamento na altura.
     */
    val startDate: String, // Início do tratamento

    /**
     * Data de fim do tratamento na altura.
     */
    val endDate: String, // Fim do tratamento

    /**
     * Um tipo mais detalhado da ação (se necessário).
     */
    val actionType: String, // Tipo específico da ação

    /**
     * Apontamentos ou notas sobre este registo.
     */
    val notes: String // Notas extra
)


