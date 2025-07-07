package com.example.mediplan.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mediplan.RoomDB.MedicationData
import com.example.mediplan.RoomDB.RoomDao
import kotlinx.coroutines.launch

class MedicationViewModel(private val repository: Repository): ViewModel() {
    fun getNotes() = repository.getAllMedication().asLiveData(viewModelScope.coroutineContext)

    fun upsertNote(medication: MedicationData){
        viewModelScope.launch {
            repository.upsertNote(medication)
        }
    }

    fun deleteNote(medication: MedicationData){
        viewModelScope.launch {
            repository.deleteNote(medication)
        }
    }

}