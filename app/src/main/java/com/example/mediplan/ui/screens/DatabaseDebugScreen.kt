package com.example.mediplan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediplan.UserDatabase
import com.example.mediplan.ViewModel.MedicationViewModel
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatabaseDebugScreen() {
    val context = LocalContext.current
    val database = UserDatabase.getDatabase(context)
    val repository = Repository(database.dao)
    val userViewModel = remember { UserViewModel(repository) }
    val medicationViewModel = remember { MedicationViewModel(repository) }

    // Observar dados
    val allMedications by medicationViewModel.getAllMedications().observeAsState(emptyList())
    
    // Estado para mostrar utilizadores (precisa de método no Repository)
    var usersCount by remember { mutableStateOf(0) }
    var medicationsCount by remember { mutableStateOf(0) }
    var historyCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        // Contar registos (pode implementar estes métodos no Repository)
        medicationsCount = allMedications.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Debug da Base de Dados",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Estatísticas:", fontWeight = FontWeight.Bold)
                Text("Medicamentos: ${allMedications.size}")
                Text("Utilizadores: $usersCount")
                Text("Histórico: $historyCount")
            }
        }

        Text(
            text = "Medicamentos:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(allMedications) { medication ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("ID: ${medication.id}", fontWeight = FontWeight.Bold)
                        Text("Nome: ${medication.medName}")
                        Text("Utilizador: ${medication.userId}")
                        Text("Dosagem: ${medication.dosage}")
                        Text("Frequência: ${medication.frequency}")
                        if (medication.description.isNotEmpty()) {
                            Text("Descrição: ${medication.description}")
                        }
                        Text("Data Início: ${medication.startDate}")
                        if (medication.endDate.isNotEmpty()) {
                            Text("Data Fim: ${medication.endDate}")
                        }
                    }
                }
            }
        }
    }
}