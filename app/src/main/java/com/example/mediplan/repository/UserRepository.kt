package com.example.mediplan.repository

import com.example.mediplan.model.User

/**
 * Simple repository to manage user data.
 * In a real application, this would connect to a backend service or local database.
 */
object UserRepository {
    // For demo purposes, we'll use a mutable state to store the current user
    private var currentUser: User? = null
    
    fun getCurrentUser(): User? = currentUser
    
    fun updateUser(user: User) {
        currentUser = user
    }
    
    fun changePassword(newPassword: String): Boolean {
        currentUser?.let {
            currentUser = it.copy(password = newPassword)
            return true
        }
        return false
    }
    
    fun deleteAccount(): Boolean {
        currentUser = null
        return true
    }
    
    fun logout(): Boolean {
        // In a real app, this would clear authentication tokens, etc.
        return true
    }
}