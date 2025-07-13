package com.example.mediplan.model

// modelo de dados para medicamentos
data class Medication(
    val id: Int = 0,
    val name: String,
    val description: String,
    val dosage: String,
    val frequency: String = "",
    val startDate: String = "",
    val endDate: String = ""
)