package com.example.mediplan.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediplan.R
import com.example.mediplan.RoomDB.UserData
import com.example.mediplan.UserDatabase
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ViewModel.UserViewModel
import com.example.mediplan.ui.components.GradientButton
import com.example.mediplan.ui.theme.LightBlue
import com.example.mediplan.ui.theme.LightGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}

) {
    var name by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf(LocalDate.now()) }
    val repository:Repository=Repository(UserDatabase)
    val viewModel=UserViewModel(repository)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo and App Name
            Image(
                painter = painterResource(id = R.drawable.mediplan_logo),
                contentDescription = "MediPlan Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "MediPlan",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = LightGreen,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Login Form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Dados Pessoais",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    //Name Field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Email", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LightGreen,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = LightGreen,
                            unfocusedLabelColor = Color.Black,
                            cursorColor = LightGreen,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                        )
                    )


                }

                DateOfBirthPicker()

                // Login Button
                GradientButton(
                    text = "Confirmar",
                    onClick = {
                        // Verificar se os campos estão preenchidos
                        if (name.isNullOrEmpty() && dateOfBirth<LocalDate.now()) {

                            // Chamar o callback de login
                            onLoginClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                )
            }
        }

        // Sign Up Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                color = Color.Black,
                fontSize = 14.sp
            )

            TextButton(onClick = onSignUpClick) {
                Text(
                    text = "Sign Up",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateOfBirthPicker() {
    var dateOfBirth by remember { mutableStateOf(LocalDate.now()) }

    val context = LocalContext.current
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateOfBirth = LocalDate.of(year, month + 1, dayOfMonth)
        },
        dateOfBirth.year,
        dateOfBirth.monthValue - 1,
        dateOfBirth.dayOfMonth
    )

    TextField(
        value = dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        onValueChange = {},
        readOnly = true,
        label = { Text("Data de Nascimento") },
        modifier = Modifier.clickable {
            datePickerDialog.show()
        }
    )
}



