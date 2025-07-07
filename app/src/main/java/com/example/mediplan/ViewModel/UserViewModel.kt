package com.example.mediplan.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediplan.RoomDB.UserData
import kotlinx.coroutines.launch


class UserViewModel(private val repository: Repository): ViewModel(){
    fun upsertUser(user:UserData){
        viewModelScope.launch {
            repository.CreateUser(user)
        }
    }
}