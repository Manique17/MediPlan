package com.example.mediplan.ui.screens

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.mediplan.ViewModel.LoginState
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ViewModel.UserViewModel
import com.example.mediplan.ui.components.AdaptiveOutlinedTextField
import com.example.mediplan.ui.components.GradientButton
import com.example.mediplan.ui.theme.LightGreen
import com.example.mediplan.ui.theme.White // Assuming White is defined in your theme

//função composable para a tela de login
@Composable
fun LoginScreen(
    userViewModel: UserViewModel? = null, // ViewModel for user authentication
    onLoginClick: (String) -> Unit = {}, // Callback when login is successful, passing user ID
    onSignUpClick: () -> Unit = {}, // Callback to navigate to sign up screen
    onForgotPasswordClick: () -> Unit = {} // Callback to navigate to forgot password screen
) {
    // apanha o contexto atual da aplicação
    val context = LocalContext.current
    val database = UserDatabase.getDatabase(context)
    val repository = Repository(database.dao)
    // Use provided userViewModel or create a new one, remembered across recompositions
    val viewModel = userViewModel ?: remember { UserViewModel(repository) }

    // email palavra-passe e visibilidade da palavra-passe
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // coleção de estados do ViewModel
    val loginState by viewModel.loginState.collectAsState()


    // obeservação do estado de login
    LaunchedEffect(loginState) {
        when (val state = loginState) { // Use 'state' for smart casting
            is LoginState.Success -> {
                // On successful login, get current user details
                val currentUser = viewModel.currentUser.value
                if (currentUser != null) {
                    onLoginClick(currentUser.id) // Trigger callback with user ID
                }
                viewModel.resetLoginState() // Reset login state in ViewModel
            }
            is LoginState.Error -> {
                // On error, log the error and set the error message to display
                Log.d("LoginScreen_Error", state.message) // More specific Log tag
                errorMessage = state.message
            }
            is LoginState.Loading -> {
                // While loading, clear any previous error message
                errorMessage = ""
            }
            LoginState.Idle -> {
                // In idle state, clear any error message
                errorMessage = ""
            }
        }
    }

    // tela de login entrada email e palavra-passe
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White) // Set background color for the screen
    ) {
        // conteudo da tela de login
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Padding around the content
            horizontalAlignment = Alignment.CenterHorizontally, // Center children horizontally
            verticalArrangement = Arrangement.Center // Center children vertically
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.mediplan_logo), // Your app logo resource
                contentDescription = "MediPlan Logo",
                modifier = Modifier
                    .size(120.dp) // Size of the logo
                    .padding(bottom = 16.dp) // Space below the logo
            )

            // Nome do App
            Text(
                text = "MediPlan",
                fontSize = 32.sp, // Font size for the app name
                fontWeight = FontWeight.Bold, // Bold font weight
                color = LightGreen, // Color for the app name text
                modifier = Modifier.padding(bottom = 32.dp) // Space below the app name
            )

            // Login Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Horizontal padding for the card
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Shadow elevation
                colors = CardDefaults.cardColors(containerColor = White) // Background color of the card
            ) {
                // coteudo do card de login
                Column(
                    modifier = Modifier
                        .padding(24.dp) // Padding inside the card
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // "Welcome Back" Text
                    Text(
                        text = "Welcome Back",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray, // Color for the welcome text
                        modifier = Modifier.padding(bottom = 24.dp) // Space below the text
                    )

                    // campo de entrada de email
                    AdaptiveOutlinedTextField( // Custom TextField component
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.Black) },
                        placeholder = { Text("Enter your email", color = Color.Gray) }, // Placeholder text color
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp), // Space below the email field
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email, // Set keyboard type to email
                            imeAction = ImeAction.Next // Action button on keyboard (e.g., "Next")
                        ),
                        singleLine = true, // Ensure single line input
                        textColor = Color.Black // Text color for the input
                    )

                    // campo de entrada de palavra-passe
                    AdaptiveOutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.Black) },
                        placeholder = { Text("Enter your password", color = Color.Gray) },
                        textColor = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp), // Space below the password field
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Toggle password visibility
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password, // Set keyboard type to password
                            imeAction = ImeAction.Done // Action button on keyboard (e.g., "Done")
                        ),
                        singleLine = true,
                        trailingIcon = { // Icon to toggle password visibility
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility // Icons for visibility
                                    ),
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )

                    // peder senha link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End // Align to the end of the row
                    ) {
                        TextButton(onClick = onForgotPasswordClick) { // Navigate on click
                            Text(
                                text = "Forgot Password?",
                                color = LightGreen, // Color for the link text
                                fontSize = 14.sp
                            )
                        }
                    }

                    // mensagem de erro
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red, // Error messages in red
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp) // Vertical padding for error message
                        )
                    }

                    // butão de login
                    GradientButton( // Custom button component
                        text = if (loginState is LoginState.Loading) "LOGGING IN..." else "LOGIN", // Dynamic button text
                        onClick = {
                            // Basic validation before attempting login
                            if (email.isNotBlank() && password.isNotBlank()) {
                                viewModel.loginUser(email, password) // Call ViewModel to log in
                            } else {
                                errorMessage = "Please fill in all fields." // Show error if fields are empty
                            }
                        },
                        enabled = loginState !is LoginState.Loading, // Disable button during loading
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(25.dp)) // Rounded corners for the button
                            .padding(top = 8.dp) // Space above the login button
                    )

                    // indicador de progresso
                    if (loginState is LoginState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 16.dp), // Space above the indicator
                            color = LightGreen // Color for the progress indicator
                        )
                    }
                }
            }

            // registro de utilizador
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp), // Increased space above the sign-up section
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                TextButton(onClick = onSignUpClick) { // Navigate to sign up on click
                    Text(
                        text = "Sign Up",
                        color = LightGreen, // Sign up link color matching theme
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold // Make "Sign Up" bold
                    )
                }
            }
        }
    }
}
// função de preview para a tela de login
@Preview(showBackground = true, widthDp = 360, heightDp = 640) // Standard device size for preview
@Composable
fun LoginScreenPreview() {
    // tela de login com valores de exemplo
    LoginScreen()

}
