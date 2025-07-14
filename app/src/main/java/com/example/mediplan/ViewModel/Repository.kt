package com.example.mediplan.ViewModel

import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.MedicationHistoryData
import com.example.mediplan.RoomDB.RoomDao
import com.example.mediplan.RoomDB.UserData
import kotlinx.coroutines.flow.Flow

/**
 * Repositório para gerir as operações de dados da aplicação.
 * Este classe abstrai as fontes de dados (neste caso, o Room DAO) do resto da aplicação.
 *
 * @property dao O Data Access Object (DAO) para interagir com a base de dados Room.
 */
class Repository(private val dao: RoomDao) {

    //region Operações do Utilizador

    /**
     * Insere um novo utilizador na base de dados.
     * @param user O objeto [UserData] a ser inserido.
     */
    suspend fun insertUser(user: UserData) {
        dao.insertUser(user)
    }

    /**
     * Atualiza um utilizador existente na base de dados.
     * @param user O objeto [UserData] com os dados atualizados.
     */
    suspend fun updateUser(user: UserData) {
        dao.updateUser(user)
    }

    /**
     * Elimina um utilizador da base de dados.
     * @param user O objeto [UserData] a ser eliminado.
     */
    suspend fun deleteUser(user: UserData) {
        dao.deleteUser(user)
    }

    /**
     * Tenta autenticar um utilizador com o email e palavra-passe fornecidos.
     * @param email O email do utilizador.
     * @param password A palavra-passe do utilizador.
     * @return O objeto [UserData] se o login for bem-sucedido, caso contrário null.
     */
    suspend fun loginUser(email: String, password: String): UserData? {
        return dao.loginUser(email, password)
    }

    /**
     * Obtém um utilizador da base de dados pelo seu email.
     * @param email O email do utilizador a ser procurado.
     * @return O objeto [UserData] se encontrado, caso contrário null.
     */
    suspend fun getUserByEmail(email: String): UserData? {
        return dao.getUserByEmail(email)
    }

    /**
     * Obtém um utilizador da base de dados pelo seu ID.
     * @param userId O ID do utilizador a ser procurado.
     * @return O objeto [UserData] se encontrado, caso contrário null.
     */
    suspend fun getUserById(userId: String): UserData? {
        return dao.getUserById(userId)
    }
    //endregion

    //region Operações da Medicação

    /**
     * Insere uma nova medicação na base de dados.
     * @param medication O objeto [MedicationData] a ser inserido.
     */
    suspend fun insertMedication(medication: MedicationData) {
        dao.insertMedication(medication)
    }

    /**
     * Atualiza uma medicação existente na base de dados.
     * @param medication O objeto [MedicationData] com os dados atualizados.
     */
    suspend fun updateMedication(medication: MedicationData) {
        dao.updateMedication(medication)
    }

    /**
     * Elimina uma medicação da base de dados.
     * @param medication O objeto [MedicationData] a ser eliminado.
     */
    suspend fun deleteMedication(medication: MedicationData) {
        dao.deleteMedication(medication)
    }

    /**
     * Obtém um Flow com a lista de medicações para um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Um Flow que emite uma lista de [MedicationData].
     */
    fun getMedicationsByUser(userId: String): Flow<List<MedicationData>> {
        return dao.getMedicationsByUser(userId)
    }

    /**
     * Obtém um Flow com a lista de todas as medicações na base de dados.
     * @return Um Flow que emite uma lista de [MedicationData].
     */
    fun getAllMedications(): Flow<List<MedicationData>> {
        return dao.getAllMedications()
    }

    /**
     * Obtém uma medicação da base de dados pelo seu ID.
     * @param medicationId O ID da medicação a ser procurada.
     * @return O objeto [MedicationData] se encontrado, caso contrário null.
     */
    suspend fun getMedicationById(medicationId: Int): MedicationData? {
        return dao.getMedicationById(medicationId)
    }

    /**
     * Elimina todas as medicações de um utilizador específico da base de dados.
     * @param userId O ID do utilizador cujas medicações serão eliminadas.
     */
    suspend fun deleteAllMedicationsForUser(userId: String) {
        dao.deleteAllMedicationsForUser(userId)
    }
    //endregion

    //region Operações do Histórico de Medicação

    /**
     * Insere um novo registo de histórico de medicação na base de dados.
     * @param history O objeto [MedicationHistoryData] a ser inserido.
     */
    suspend fun insertMedicationHistory(history: MedicationHistoryData) {
        dao.insertMedicationHistory(history)
    }

    /**
     * Obtém um Flow com a lista de histórico de medicação para um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Um Flow que emite uma lista de [MedicationHistoryData].
     */
    fun getMedicationHistoryByUser(userId: String): Flow<List<MedicationHistoryData>> {
        return dao.getMedicationHistoryByUser(userId)
    }

    /**
     * Obtém um Flow com a lista de histórico de medicação para um utilizador específico, filtrado por tipo de ação.
     * @param userId O ID do utilizador.
     * @param actionType O tipo de ação a ser filtrado (ex: "TAKEN", "SKIPPED").
     * @return Um Flow que emite uma lista de [MedicationHistoryData].
     */
    fun getMedicationHistoryByUserAndType(userId: String, actionType: String): Flow<List<MedicationHistoryData>> {
        return dao.getMedicationHistoryByUserAndType(userId, actionType)
    }

    /**
     * Elimina todo o histórico de medicação de um utilizador específico da base de dados.
     * @param userId O ID do utilizador cujo histórico será eliminado.
     */
    suspend fun deleteAllHistoryForUser(userId: String) {
        dao.deleteAllHistoryForUser(userId)
    }

    /**
     * Elimina um registo específico do histórico de medicação da base de dados.
     * @param history O objeto [MedicationHistoryData] a ser eliminado.
     */
    suspend fun deleteMedicationHistory(history: MedicationHistoryData) {
        dao.deleteMedicationHistory(history)
    }

    /**
     * Obtém todos os utilizadores da base de dados.
     * @return Uma lista de [UserData].
     */
    suspend fun getAllUsers(): List<UserData> {
        return dao.getAllUsers()
    }

    /**
     * Conta o número total de registos de histórico.
     * @return O número total de registos de histórico.
     */
    suspend fun getHistoryCount(): Int {
        return dao.getHistoryCount()
    }

    /**
     * Conta o número total de utilizadores.
     * @return O número total de utilizadores.
     */
    suspend fun getUsersCount(): Int {
        return dao.getUsersCount()
    }
    //endregion
}
