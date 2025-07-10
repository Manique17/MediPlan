
package com.example.mediplan.ViewModel

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediplan.RoomDB.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: Repository) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> = _currentUser.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    fun insertUser(user: UserData) {
        viewModelScope.launch {
            try {
                repository.insertUser(user)
                _signUpState.value = SignUpState.Success
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                val user = repository.loginUser(email, password)
                if (user != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        _currentUser.value = user
                    }
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun signUpUser(name: String, email: String, password: String, dateOfBirth: String) {
        viewModelScope.launch {
            try {
                _signUpState.value = SignUpState.Loading

                val existingUser = repository.getUserByEmail(email)
                if (existingUser != null) {
                    _signUpState.value = SignUpState.Error("User with this email already exists")
                    return@launch
                }

                val newUser = UserData(
                    id = java.util.UUID.randomUUID().toString(),
                    name = name,
                    email = email,
                    password = password,
                    dateOfBirth = dateOfBirth
                )

                repository.insertUser(newUser)
                _signUpState.value = SignUpState.Success
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateUser(user: UserData) {
        viewModelScope.launch {
            try {
                repository.updateUser(user)
                _currentUser.value = user
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteUser(user: UserData) {
        viewModelScope.launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    repository.deleteAllMedicationsForUser(user.id)
                }
                repository.deleteUser(user)
                _currentUser.value = null
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _loginState.value = LoginState.Idle
        _signUpState.value = SignUpState.Idle
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun resetSignUpState() {
        _signUpState.value = SignUpState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}