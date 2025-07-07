package com.example.mediplan.model

import java.util.Date

data class Medication(
    val name: String,
    val dosage: String,
    val date: Date,
    val times: List<Pair<Int, Int>>, // Lista de horários (hora e minuto)
    val status: MedicationStatus = MedicationStatus.PENDING,
    val takenAt: Date? = null // Data em que o medicamento foi tomado
)

enum class MedicationStatus {
    PENDING,  // Medicamento pendente (no plano)
    TAKEN,    // Medicamento tomado (no histórico)
    DELETED   // Medicamento excluído (também vai para o histórico)
}