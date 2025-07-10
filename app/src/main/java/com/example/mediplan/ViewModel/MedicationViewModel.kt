package com.example.mediplan.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.MedicationHistoryData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MedicationViewModel(private val repository: Repository) : ViewModel() {
    
    private val _medicationState = MutableStateFlow<MedicationState>(MedicationState.Idle)
    val medicationState: StateFlow<MedicationState> = _medicationState.asStateFlow()
    
    fun getMedicationsByUser(userId: String) = repository.getMedicationsByUser(userId).asLiveData(viewModelScope.coroutineContext)
    
    fun getAllMedications() = repository.getAllMedications().asLiveData(viewModelScope.coroutineContext)
    
    fun getMedicationHistoryByUser(userId: String) = repository.getMedicationHistoryByUser(userId).asLiveData(viewModelScope.coroutineContext)
    
    fun getMedicationHistoryByUserAndType(userId: String, actionType: String) = repository.getMedicationHistoryByUserAndType(userId, actionType).asLiveData(viewModelScope.coroutineContext)
    
    fun insertMedication(medication: MedicationData) {
        viewModelScope.launch {
            try {
                repository.insertMedication(medication)
                _medicationState.value = MedicationState.Success("Medication added successfully")
            } catch (e: Exception) {
                _medicationState.value = MedicationState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun updateMedication(medication: MedicationData) {
        viewModelScope.launch {
            try {
                repository.updateMedication(medication)
                _medicationState.value = MedicationState.Success("Medication updated successfully")
            } catch (e: Exception) {
                _medicationState.value = MedicationState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun deleteMedication(medication: MedicationData) {
        viewModelScope.launch {
            try {
                // Add to history before deleting
                val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
                val history = MedicationHistoryData(
                    medName = medication.medName,
                    description = medication.description,
                    dosage = medication.dosage,
                    frequency = medication.frequency,
                    startDate = medication.startDate,
                    endDate = medication.endDate,
                    userId = medication.userId,
                    actionType = "DELETED",
                    actionDate = currentDate,
                    notes = "Medicamento removido pelo usuário"
                )
                repository.insertMedicationHistory(history)
                
                repository.deleteMedication(medication)
                _medicationState.value = MedicationState.Success("Medication deleted successfully")
            } catch (e: Exception) {
                _medicationState.value = MedicationState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun markMedicationAsTaken(medication: MedicationData, notes: String = "") {
        viewModelScope.launch {
            try {
                val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
                val history = MedicationHistoryData(
                    medName = medication.medName,
                    description = medication.description,
                    dosage = medication.dosage,
                    frequency = medication.frequency,
                    startDate = medication.startDate,
                    endDate = medication.endDate,
                    userId = medication.userId,
                    actionType = "TAKEN",
                    actionDate = currentDate,
                    notes = notes.ifEmpty { "Medicamento tomado" }
                )
                repository.insertMedicationHistory(history)
                _medicationState.value = MedicationState.Success("Medication marked as taken")
            } catch (e: Exception) {
                _medicationState.value = MedicationState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun markMedicationAsCompleted(medication: MedicationData) {
        viewModelScope.launch {
            try {
                val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
                val history = MedicationHistoryData(
                    medName = medication.medName,
                    description = medication.description,
                    dosage = medication.dosage,
                    frequency = medication.frequency,
                    startDate = medication.startDate,
                    endDate = medication.endDate,
                    userId = medication.userId,
                    actionType = "COMPLETED",
                    actionDate = currentDate,
                    notes = "Tratamento concluído"
                )
                repository.insertMedicationHistory(history)
                
                // Remove from active medications
                repository.deleteMedication(medication)
                _medicationState.value = MedicationState.Success("Treatment completed successfully")
            } catch (e: Exception) {
                _medicationState.value = MedicationState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun getMedicationById(medicationId: Int, callback: (MedicationData?) -> Unit) {
        viewModelScope.launch {
            try {
                val medication = repository.getMedicationById(medicationId)
                callback(medication)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }
    
    fun resetState() {
        _medicationState.value = MedicationState.Idle
    }
}

sealed class MedicationState {
    object Idle : MedicationState()
    data class Success(val message: String) : MedicationState()
    data class Error(val message: String) : MedicationState()
}