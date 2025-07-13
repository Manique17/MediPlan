package com.example.mediplan.model

import java.time.LocalDate

//modelo de dados para usuários
data class User(
    val id: String = "",
    val name: String,
    val email: String,
    val password: String,
    val birthday: LocalDate
)