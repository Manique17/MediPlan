package com.example.mediplan.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.mediplan.model.Medication
import com.example.mediplan.model.MedicationStatus
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Repositório simples para gerenciar medicamentos.
 * Em uma aplicação real, isso seria conectado a um banco de dados.
 */
object MedicationRepository {
    // Lista observável de medicamentos
    private val _medications = mutableStateListOf<Medication>()
    
    // Constante para o período de retenção do histórico (7 dias em milissegundos)
    private const val HISTORY_RETENTION_PERIOD_MS = 7 * 24 * 60 * 60 * 1000L
    
    // Lista pública somente leitura de todos os medicamentos
    val medications: List<Medication>
        get() {
            // Limpar histórico antigo antes de retornar a lista
            cleanupOldHistory()
            return _medications.toList()
        }
    
    // Lista de medicamentos pendentes (plano)
    val pendingMedications: List<Medication>
        get() {
            // Limpar histórico antigo antes de retornar a lista
            cleanupOldHistory()
            return _medications.filter { it.status == MedicationStatus.PENDING }
        }
    
    // Lista de medicamentos tomados ou excluídos (histórico)
    val takenMedications: List<Medication>
        get() {
            // Limpar histórico antigo antes de retornar a lista
            cleanupOldHistory()
            return _medications.filter { it.status == MedicationStatus.TAKEN || it.status == MedicationStatus.DELETED }
        }
    
    // Adicionar um novo medicamento
    fun addMedication(medication: Medication) {
        _medications.add(medication)
    }
    
    // Atualizar um medicamento existente
    fun updateMedication(oldMedication: Medication, newMedication: Medication) {
        val index = _medications.indexOf(oldMedication)
        if (index != -1) {
            _medications[index] = newMedication
        }
    }
    
    // Marcar um medicamento como tomado
    fun markAsTaken(medication: Medication) {
        val index = _medications.indexOf(medication)
        if (index != -1) {
            // Criar uma nova versão do medicamento com status TAKEN
            val takenMedication = medication.copy(
                status = MedicationStatus.TAKEN,
                takenAt = Date() // Data atual
            )
            _medications[index] = takenMedication
        }
    }
    
    // Marcar um medicamento como excluído (vai para o histórico)
    fun removeMedication(medication: Medication) {
        val index = _medications.indexOf(medication)
        if (index != -1) {
            // Criar uma nova versão do medicamento com status DELETED
            val deletedMedication = medication.copy(
                status = MedicationStatus.DELETED,
                takenAt = Date() // Data atual da exclusão
            )
            _medications[index] = deletedMedication
        }
    }
    
    // Limpar todos os medicamentos (para testes)
    fun clearAll() {
        _medications.clear()
    }
    
    // Limpar histórico antigo (medicamentos tomados ou excluídos há mais de 7 dias)
    private fun cleanupOldHistory() {
        val currentTime = System.currentTimeMillis()
        val toRemove = mutableListOf<Medication>()
        
        // Identificar medicamentos no histórico com mais de 7 dias
        for (medication in _medications) {
            if ((medication.status == MedicationStatus.TAKEN || medication.status == MedicationStatus.DELETED) 
                && medication.takenAt != null) {
                
                val medicationTime = medication.takenAt.time
                val timeDifference = currentTime - medicationTime
                
                // Se o medicamento foi tomado/excluído há mais de 7 dias, adicionar à lista de remoção
                if (timeDifference > HISTORY_RETENTION_PERIOD_MS) {
                    toRemove.add(medication)
                }
            }
        }
        
        // Remover os medicamentos antigos da lista
        _medications.removeAll(toRemove)
    }
}