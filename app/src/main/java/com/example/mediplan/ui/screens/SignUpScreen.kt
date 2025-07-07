package com.example.mediplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediplan.R
import com.example.mediplan.UserDatabase
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ViewModel.SignUpState
import com.example.mediplan.ViewModel.UserViewModel
import com.example.mediplan.ui.components.AdaptiveOutlinedTextField
import com.example.mediplan.ui.components.GradientButton
import com.example.mediplan.ui.theme.LightGreen
import com.example.mediplan.ui.theme.White

// Helper function to validate birthdate format (MM/DD/YYYY)
fun isValidBirthdate(birthdate: String): Boolean {
    if (birthdate.isEmpty()) return true
    if (!birthdate.matches(Regex("^\\d{2}/\\d{2}/\\d{4}$"))) return true
    
    try {
        val parts = birthdate.split("/")
        val month = parts[0].toInt()
        val day = parts[1].toInt()
        val year = parts[2].toInt()
        
        if (month < 1 || month > 12) return false
        if (day < 1 || day > 31) return false
        if (year < 1900 || year > 2100) return false
        
        val daysInMonth = when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
        
        return day <= daysInMonth
    } catch (e: Exception) {
        return false
    }
}

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val database = UserDatabase.getDatabase(context)
    val repository = Repository(database.dao)
    val viewModel = remember { UserViewModel(repository) }
    
    val scrollState = rememberScrollState()
    
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdateText by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val signUpState by viewModel.signUpState.collectAsState()
    
    // Handle sign up state changes
    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                onSignUpClick()
                viewModel.resetSignUpState()
            }
            is SignUpState.Error -> {
                errorMessage = (signUpState as SignUpState.Error).message
            }
            else -> {
                errorMessage = ""
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo and App Name
            Image(
                painter = painterResource(id = R.drawable.mediplan_logo),
                contentDescription = "MediPlan Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 8.dp)
            )
            
            Text(
                text = "MediPlan",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = LightGreen,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Sign Up Form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    // Full Name Field
                    AdaptiveOutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                    
                    // Email Field
                    AdaptiveOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                    
                    // Birthdate Field
                    AdaptiveOutlinedTextField(
                        value = birthdateText,
                        onValueChange = { newValue -> 
                            val filtered = newValue.filter { it.isDigit() }.take(8)
                            
                            birthdateText = when {
                                filtered.length > 4 -> "${filtered.take(2)}/${filtered.substring(2, 4)}/${filtered.substring(4)}"
                                filtered.length > 2 -> "${filtered.take(2)}/${filtered.substring(2)}"
                                else -> filtered
                            }
                        },
                        label = { Text("Birthdate (MM/DD/YYYY)") },
                        placeholder = { Text("Example: 01/31/2000") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        isError = !isValidBirthdate(birthdateText),
                        supportingText = {
                            if (!isValidBirthdate(birthdateText)) {
                                Text("Please enter a valid date", color = Color.Red)
                            }
                        }
                    )
                    
                    // Password Field
                    AdaptiveOutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                                    ),
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    )
                    
                    // Confirm Password Field
                    AdaptiveOutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (confirmPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                                    ),
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    )
                    
                    // Error Message
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    
                    // Sign Up Button
                    GradientButton(
                        text = if (signUpState is SignUpState.Loading) "CREATING ACCOUNT..." else "SIGN UP",
                        onClick = {
                            when {
                                fullName.isBlank() -> errorMessage = "Please enter your full name"
                                email.isBlank() -> errorMessage = "Please enter your email"
                                !isValidBirthdate(birthdateText) || birthdateText.length != 10 -> errorMessage = "Please enter a valid birthdate"
                                password.isBlank() -> errorMessage = "Please enter a password"
                                password != confirmPassword -> errorMessage = "Passwords do not match"
                                password.length < 6 -> errorMessage = "Password must be at least 6 characters"
                                else -> {
                                    viewModel.signUpUser(fullName, email, password, birthdateText)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                    )
                    
                    // Loading indicator
                    if (signUpState is SignUpState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 16.dp),
                            color = LightGreen
                        )
                    }
                }
            }
            
            // Login Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                
                TextButton(onClick = onLoginClick) {
                    Text(
                        text = "Login",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen()
}