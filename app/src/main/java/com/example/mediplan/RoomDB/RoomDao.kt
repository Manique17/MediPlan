package com.example.mediplan.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO (Data Access Object) para interagir com a base de dados.
 * Define métodos para realizar operações na base de dados (inserir, atualizar, excluir, consultar).
 */
@Dao // Marca esta interface como um DAO para o Room
interface RoomDao {

    // --- Operações relacionadas com Utilizadores (Users) ---

    /**
     * Insere um novo utilizador na tabela 'users'.
     * Se um utilizador com o mesmo ID já existir, ele será substituído.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Insere dados. Se houver conflito (ex: ID igual), substitui.
    suspend fun insertUser(user: UserData) // 'suspend' indica que é uma função de corrotina (para operações assíncronas)

    /**
     * Atualiza os dados de um utilizador existente.
     */
    @Update // Atualiza dados existentes
    suspend fun updateUser(user: UserData)

    /**
     * Exclui um utilizador da base de dados.
     */
    @Delete // Exclui dados
    suspend fun deleteUser(user: UserData)

    /**
     * Procura um utilizador pelo email e password (para login).
     * Retorna o utilizador encontrado ou 'null' se não encontrar.
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1") // Consulta SQL personalizada
    suspend fun loginUser(email: String, password: String): UserData? // O '?' significa que pode retornar nulo

    /**
     * Procura um utilizador pelo seu email.
     * Retorna o utilizador encontrado ou 'null'.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserData?

    /**
     * Procura um utilizador pelo seu ID.
     * Retorna o utilizador encontrado ou 'null'.
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserData?

    // --- Operações relacionadas com Medicações (Medications) ---

    /**
     * Insere uma nova medicação. Substitui se já existir uma com o mesmo ID.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationData)

    /**
     * Atualiza os dados de uma medicação existente.
     */
    @Update
    suspend fun updateMedication(medication: MedicationData)

    /**
     * Exclui uma medicação da base de dados.
     */
    @Delete
    suspend fun deleteMedication(medication: MedicationData)

    /**
     * Obtém todas as medicações de um utilizador específico.
     * Retorna um Flow, que permite observar mudanças na lista de medicações em tempo real.
     */
    @Query("SELECT * FROM medications WHERE userId = :userId")
    fun getMedicationsByUser(userId: String): Flow<List<MedicationData>> // Flow para dados que mudam

    /**
     * Obtém todas as medicações de todos os utilizadores.
     * Retorna um Flow.
     */
    @Query("SELECT * FROM medications")
    fun getAllMedications(): Flow<List<MedicationData>>

    /**
     * Obtém uma medicação específica pelo seu ID.
     * Retorna a medicação ou 'null'.
     */
    @Query("SELECT * FROM medications WHERE id = :medicationId LIMIT 1")
    suspend fun getMedicationById(medicationId: Int): MedicationData?

    /**
     * Exclui todas as medicações de um utilizador específico.
     */
    @Query("DELETE FROM medications WHERE userId = :userId")
    suspend fun deleteAllMedicationsForUser(userId: String)

    // --- Operações relacionadas com o Histórico de Medicações (Medication History) ---

    /**
     * Insere um novo registo no histórico de medicações. Substitui se já existir um com o mesmo ID.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicationHistory(history: MedicationHistoryData)

    /**
     * Exclui um registo do histórico de medicações.
     */
    @Delete
    suspend fun deleteMedicationHistory(history: MedicationHistoryData)

    /**
     * Obtém todo o histórico de medicações de um utilizador, ordenado pela data da ação (mais recente primeiro).
     * Retorna um Flow.
     */
    @Query("SELECT * FROM medication_history WHERE userId = :userId ORDER BY actionDate DESC")
    fun getMedicationHistoryByUser(userId: String): Flow<List<MedicationHistoryData>>

    /**
     * Obtém o histórico de medicações de um utilizador para um tipo de ação específico,
     * ordenado pela data da ação (mais recente primeiro).
     * Retorna um Flow.
     */
    @Query("SELECT * FROM medication_history WHERE userId = :userId AND action = :actionType ORDER BY actionDate DESC")
    fun getMedicationHistoryByUserAndType(userId: String, actionType: String): Flow<List<MedicationHistoryData>>

    /**
     * Exclui todo o histórico de medicações de um utilizador específico.
     */
    @Query("DELETE FROM medication_history WHERE userId = :userId")
    suspend fun deleteAllHistoryForUser(userId: String)
}

