package com.example.mediplan.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mediplan.RoomDB.MedicationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicationViewModel(private val repository: Repository) : ViewModel() {
    
    private val _medicationState = MutableStateFlow<MedicationState>(MedicationState.Idle)
    val medicationState: StateFlow<MedicationState> = _medicationState.asStateFlow()
    
    fun getMedicationsByUser(userId: String) = repository.getMedicationsByUser(userId).asLiveData(viewModelScope.coroutineContext)
    
    fun getAllMedications() = repository.getAllMedications().asLiveData(viewModelScope.coroutineContext)
    
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
                repository.deleteMedication(medication)
                _medicationState.value = MedicationState.Success("Medication deleted successfully")
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