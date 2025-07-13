package com.example.mediplan.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa uma medicação na base de dados.
 * O Room usa esta classe para criar uma tabela chamada "medications".
 */
@Entity(tableName = "medications") // Define o nome da tabela no banco de dados
data class MedicationData(
    /**
     * ID único da medicação (gerado automaticamente pelo Room).
     */
    @PrimaryKey(autoGenerate = true) // Torna 'id' a chave principal e auto-incrementável
    val id: Int = 0, // O ID da medicação

    /**
     * ID do utilizador que possui esta medicação.
     */
    val userId: String, // Identificador do utilizador

    /**
     * Nome da medicação (ex: "Paracetamol").
     */
    val medName: String, // Nome do medicamento

    /**
     * Dosagem da medicação (ex: "500mg", "1 comprimido").
     */
    val dosage: String, // Quantidade a ser tomada

    /**
     * Com que frequência tomar (ex: "A cada 8 horas").
     */
    val frequency: String, // Frequência de uso

    /**
     * Notas ou detalhes extras sobre a medicação (opcional).
     */
    val description: String = "", // Descrição adicional (opcional)

    /**
     * Data em que o uso da medicação começou (opcional, formato de texto ex: "2023-10-27").
     */
    val startDate: String = "", // Data de início (opciona)

    /**
     * Data em que o uso da medicação terminou (opcional, formato de texto ex: "2023-11-27").
     */
    val endDate: String = "" // Data de término (opciona)
)


