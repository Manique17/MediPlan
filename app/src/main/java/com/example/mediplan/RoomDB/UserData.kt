package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa um utilizador na base de dados.
 * O Room usa esta classe para criar uma tabela chamada "users".
 */
@Entity(tableName = "users") // Define o nome da tabela na base de dados
data class UserData(
    /**
     * ID único do utilizador (geralmente o email ou um ID gerado).
     * Este é o identificador principal do utilizador na tabela.
     */
    @PrimaryKey // Torna 'id' a chave principal da tabela
    val id: String, // O ID do utilizador (texto)

    /**
     * Nome completo do utilizador.
     */
    val name: String, // Nome do utilizador

    /**
     * Endereço de email do utilizador.
     * Usado para login e comunicação.
     */
    val email: String, // Email do utilizador

    /**
     * Password do utilizador (deve ser armazenada de forma segura, idealmente "hasheada").
     */
    val password: String, // Password do utilizador

    /**
     * Data de nascimento do utilizador.
     * Armazenada como texto (ex: "1990-05-15").
     * Comentário original: "Using String instead of LocalDate for Room compatibility"
     * (Usar Texto em vez de LocalDate para compatibilidade com o Room) - isto significa
     * que se escolheu usar texto diretamente em vez de um objeto de Data mais complexo
     * que necessitaria de conversores (TypeConverters) para ser guardado no Room.
     */
    val dateOfBirth: String // Data de nascimento (em texto)
)

