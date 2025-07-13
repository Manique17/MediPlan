package com.example.mediplan.RoomDB

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Ajuda o Room (base de dados) a guardar e carregar datas do tipo LocalDate.
 * O Room não sabe lidar com LocalDate diretamente, então ensinamos-lhe.
 */
class Converters {
    /**
     * Um formatador para transformar datas em texto no formato "ANO-MÊS-DIA" (ex: "2023-12-25")
     * e vice-versa.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    /**
     * Transforma uma data (LocalDate) em texto (String) para salvar na base de dados.
     * Ex: LocalDate de Natal -> "2023-12-25"
     * Se a data for nula, o texto também será nulo.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter // Avisa o Room que este método converte um tipo
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter) // Usa o 'formatter' para transformar a data em texto
    }

    /**
     * Transforma um texto (String) de volta em uma data (LocalDate) ao carregar do banco de dados.
     * Ex: "2023-12-25" -> LocalDate de Natal
     * Se o texto for nulo, a data também será nula.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter // Avisa o Room que este método converte um tipo
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) } // Usa o 'formatter' para transformar o texto em data
    }
}
