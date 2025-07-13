
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

// ViewModel para gerenciar o estado do usuário
class UserViewModel(private val repository: Repository) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> = _currentUser.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Idle)
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState.asStateFlow()

    // Função para obter o usuário atual
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

    // Função para fazer login do usuário
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                val user = repository.loginUser(email, password)
                if (user != null) {
                    _currentUser.value = user
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }
    // Função para redefinir a senha do usuário
    fun signUpUser(name: String, email: String, password: String, dateOfBirth: String) {
        viewModelScope.launch {
            try {
                _signUpState.value = SignUpState.Loading

                // Validar se o email já existe
                val existingUser = repository.getUserByEmail(email)
                if (existingUser != null) {
                    _signUpState.value = SignUpState.Error("User with this email already exists")
                    return@launch
                }

                // Validar a data de nascimento
                val newUser = UserData(
                    id = java.util.UUID.randomUUID().toString(),
                    name = name,
                    email = email,
                    password = password,
                    dateOfBirth = dateOfBirth
                )

                // Inserir o novo usuário na base de dados
                repository.insertUser(newUser)
                _signUpState.value = SignUpState.Success
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message ?: "Unknown error")
            }
        }
    }
    // Função para redefinir a senha do usuário
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

    // Função para redefinir a senha do usuário
    fun deleteUser(user: UserData) {
        viewModelScope.launch {
            try {
                // Primeiro elimina todos os dados relacionados ao usuário
                repository.deleteAllMedicationsForUser(user.id)
                repository.deleteAllHistoryForUser(user.id)
                // Depois elimina o usuário
                repository.deleteUser(user)
                // Limpa o estado atual do usuário
                _currentUser.value = null
                // Log para debug
                android.util.Log.d("UserViewModel", "Usuário ${user.name} eliminado com sucesso")
            } catch (e: Exception) {
                // Log do erro para debug
                android.util.Log.e("UserViewModel", "Erro ao eliminar usuário: ${e.message}")
            }
        }
    }

    // Função para fazer logout do usuário
    fun logout() {
        _currentUser.value = null
        _loginState.value = LoginState.Idle
        _signUpState.value = SignUpState.Idle
    }

    // Função para redefinir a senha do usuário
    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    // Função para redefinir o estado de redefinição de senha
    fun resetSignUpState() {
        _signUpState.value = SignUpState.Idle
    }

        }


// Funções para gerenciar o estado de redefinição de senha
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
// Funções para gerenciar o estado de registro de usuário
sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}

// Funções para gerenciar o estado de redefinição de senha
sealed class ResetPasswordState {
    object Idle : ResetPasswordState()
    object Loading : ResetPasswordState()
    data class Success(val message: String) : ResetPasswordState()
    data class Error(val message: String) : ResetPasswordState()
}