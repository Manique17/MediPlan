package com.example.mediplan.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mediplan.model.Medication
import com.example.mediplan.model.MedicationStatus
import com.example.mediplan.ui.components.AdaptiveOutlinedTextField
import com.example.mediplan.ui.components.GradientButton
import com.example.mediplan.ui.theme.LightBlue
import com.example.mediplan.ui.theme.LightGreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale



@Composable
fun HomeScreen(
    onLogout: () -> Unit = {},
    onAccountDeleted: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add Medication") },
                    label = { Text("Add") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "See Plan") },
                    label = { Text("Plan") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Filled.AccessTime, contentDescription = "History") },
                    label = { Text("History") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
                    label = { Text("Account") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                0 -> AddMedicationScreen()
                1 -> SeePlanScreen()
                2 -> HistoryScreen()
                3 -> AccountScreen(
                    onLogout = onLogout,
                    onAccountDeleted = onAccountDeleted
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen() {
    // Estado para os campos do formulário
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var selectedTimes by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }
    
    // Estado para controlar a exibição dos diálogos
    var showTimePicker by remember { mutableStateOf(false) }
    var editingTimeIndex by remember { mutableStateOf<Int?>(null) }
    
    // Estado para mensagem de sucesso
    var showSuccessMessage by remember { mutableStateOf(false) }
    
    // Scroll state para permitir rolagem
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Adicionar Medicamento",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LightGreen,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )
        
        // Campo para nome do medicamento
        AdaptiveOutlinedTextField(
            value = medicationName,
            onValueChange = { medicationName = it },
            label = { Text("Nome do Medicamento") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // Campo para dosagem
        AdaptiveOutlinedTextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosagem (ex: 500mg, 10ml)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        
        // Título para a seção de horários
        Text(
            text = "Horários",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = LightGreen,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )
        
        // Lista de horários selecionados
        if (selectedTimes.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                selectedTimes.forEachIndexed { index, time ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Schedule,
                                    contentDescription = "Horário",
                                    tint = LightGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "${time.first.toString().padStart(2, '0')}:${time.second.toString().padStart(2, '0')}",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            Row {
                                // Botão para editar
                                TextButton(
                                    onClick = {
                                        editingTimeIndex = index
                                        showTimePicker = true
                                    }
                                ) {
                                    Text("Editar", color = LightBlue)
                                }
                                
                                // Botão para remover
                                TextButton(
                                    onClick = {
                                        selectedTimes = selectedTimes.toMutableList().apply {
                                            removeAt(index)
                                        }
                                    }
                                ) {
                                    Text("Remover", color = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Mensagem quando não há horários
            Text(
                text = "Nenhum horário adicionado",
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        
        // Botão para adicionar novo horário
        Button(
            onClick = { 
                editingTimeIndex = null
                showTimePicker = true 
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = "Adicionar Horário",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Adicionar Horário")
        }
        
        // Botão para guardar
        GradientButton(
            text = "Guardar",
            onClick = {
                // Verificar se todos os campos estão preenchidos
                if (medicationName.isNotBlank() && dosage.isNotBlank() && 
                    selectedTimes.isNotEmpty()) {
                    
                    // Criar um novo objeto Medication com a data atual
                    val medication = Medication(
                        name = medicationName,
                        dosage = dosage,
                        date = Date(), // Data atual
                        times = selectedTimes
                    )
                    
                    // Adicionar ao repositório
                    MedicationRepository.addMedication(medication)
                    
                    // Mostrar mensagem de sucesso
                    showSuccessMessage = true
                    
                    // Limpar os campos após salvar
                    medicationName = ""
                    dosage = ""
                    selectedTimes = emptyList()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        
        // Mensagem de sucesso
        if (showSuccessMessage) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Medicamento adicionado com sucesso!",
                color = LightGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
    
    // Dialog para seleção de hora
    if (showTimePicker) {
        val currentTime = if (editingTimeIndex != null && editingTimeIndex!! < selectedTimes.size) {
            selectedTimes[editingTimeIndex!!]
        } else {
            val calendar = Calendar.getInstance()
            Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        }
        
        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.first,
            initialMinute = currentTime.second
        )
        
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (editingTimeIndex != null) "Editar Horário" else "Adicionar Horário",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    TimePicker(state = timePickerState)
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Cancelar")
                        }
                        TextButton(onClick = {
                            val newTime = Pair(timePickerState.hour, timePickerState.minute)
                            
                            if (editingTimeIndex != null) {
                                // Editar horário existente
                                selectedTimes = selectedTimes.toMutableList().apply {
                                    set(editingTimeIndex!!, newTime)
                                }
                            } else {
                                // Adicionar novo horário
                                selectedTimes = selectedTimes + newTime
                            }
                            
                            showTimePicker = false
                        }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SeePlanScreen() {
    // Formatadores de data e hora
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = { time: Pair<Int, Int> -> 
        "${time.first.toString().padStart(2, '0')}:${time.second.toString().padStart(2, '0')}"
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Plano de Medicamentos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LightGreen,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
        
        // Verificar se há medicamentos pendentes
        if (MedicationRepository.pendingMedications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum medicamento pendente.\nVá para a aba 'Add' para adicionar medicamentos.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            // Lista de medicamentos pendentes
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(MedicationRepository.pendingMedications.sortedBy { it.date.time }) { medication ->
                    MedicationCard(
                        medication = medication,
                        dateFormatter = dateFormatter,
                        timeFormatter = timeFormatter,
                        showTakenButton = true,
                        onMarkAsTaken = {
                            MedicationRepository.markAsTaken(medication)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationCard(
    medication: Medication,
    dateFormatter: SimpleDateFormat,
    timeFormatter: (Pair<Int, Int>) -> String,
    showTakenButton: Boolean = false,
    onMarkAsTaken: () -> Unit = {}
) {
    // Estado para controlar a exibição do diálogo de edição
    var showEditDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = medication.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightGreen
                )
                
                // Botão de edição
                TextButton(
                    onClick = { showEditDialog = true },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        text = "Editar",
                        color = LightBlue,
                        fontSize = 14.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Dosagem: ${medication.dosage}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Data",
                    tint = LightBlue,
                    modifier = Modifier.size(16.dp)
                )
                
                Text(
                    text = dateFormatter.format(medication.date),
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            
            // Mostrar todos os horários
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Horários:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            
            medication.times.forEach { time ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Hora",
                        tint = LightBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Text(
                        text = timeFormatter(time),
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            
            // Botão "Tomado" (apenas se showTakenButton for true)
            if (showTakenButton) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onMarkAsTaken,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen
                    )
                ) {
                    Text(
                        text = "Marcar como Tomado",
                        color = Color.White
                    )
                }
            }
            
            // Mostrar data em que foi tomado ou excluído (para histórico)
            if (medication.takenAt != null) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val statusText = when (medication.status) {
                        MedicationStatus.TAKEN -> "Tomado em"
                        MedicationStatus.DELETED -> "Excluído em"
                        else -> ""
                    }
                    
                    Text(
                        text = "$statusText: ${dateFormatter.format(medication.takenAt)} às " +
                                SimpleDateFormat("HH:mm", Locale.getDefault()).format(medication.takenAt),
                        fontSize = 12.sp,
                        color = if (medication.status == MedicationStatus.DELETED) Color.Red.copy(alpha = 0.7f) else Color.Gray,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }
    }
    
    // Diálogo de edição
    if (showEditDialog) {
        EditMedicationDialog(
            medication = medication,
            onDismiss = { showEditDialog = false },
            onConfirm = { updatedMedication ->
                MedicationRepository.updateMedication(medication, updatedMedication)
                showEditDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMedicationDialog(
    medication: Medication,
    onDismiss: () -> Unit,
    onConfirm: (Medication) -> Unit
) {
    // Estado para os campos do formulário
    var medicationName by remember { mutableStateOf(medication.name) }
    var dosage by remember { mutableStateOf(medication.dosage) }
    var selectedTimes by remember { mutableStateOf(medication.times) }
    
    // Estado para controlar a exibição dos diálogos
    var showTimePicker by remember { mutableStateOf(false) }
    var editingTimeIndex by remember { mutableStateOf<Int?>(null) }
    
    // Scroll state para permitir rolagem
    val scrollState = rememberScrollState()
    
    // Verificar se o medicamento está no histórico
    val isInHistory = medication.status == MedicationStatus.TAKEN || medication.status == MedicationStatus.DELETED
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Editar Medicamento",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = LightGreen,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Campo para nome do medicamento
                OutlinedTextField(
                    value = medicationName,
                    onValueChange = { medicationName = it },
                    label = { Text("Nome do Medicamento") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                
                // Campo para dosagem
                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosagem") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                
                // Título para a seção de horários
                Text(
                    text = "Horários",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightGreen,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 8.dp)
                )
                
                // Lista de horários selecionados
                if (selectedTimes.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        selectedTimes.forEachIndexed { index, time ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 1.dp
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Schedule,
                                            contentDescription = "Horário",
                                            tint = LightGreen,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "${time.first.toString().padStart(2, '0')}:${time.second.toString().padStart(2, '0')}",
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                    
                                    Row {
                                        // Botão para editar
                                        TextButton(
                                            onClick = {
                                                editingTimeIndex = index
                                                showTimePicker = true
                                            },
                                            modifier = Modifier.padding(0.dp)
                                        ) {
                                            Text("Editar", color = LightBlue, fontSize = 12.sp)
                                        }
                                        
                                        // Botão para remover
                                        TextButton(
                                            onClick = {
                                                selectedTimes = selectedTimes.toMutableList().apply {
                                                    removeAt(index)
                                                }
                                            },
                                            modifier = Modifier.padding(0.dp)
                                        ) {
                                            Text("Remover", color = Color.Red, fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Mensagem quando não há horários
                    Text(
                        text = "Nenhum horário adicionado",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                // Botão para adicionar novo horário
                Button(
                    onClick = { 
                        editingTimeIndex = null
                        showTimePicker = true 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Adicionar Horário",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Adicionar Horário")
                }
                
                // Botões de ação
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Botão para excluir (apenas se não estiver no histórico)
                    if (!isInHistory) {
                        TextButton(
                            onClick = {
                                MedicationRepository.removeMedication(medication)
                                onDismiss()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red
                            )
                        ) {
                            Text("Excluir")
                        }
                    }
                    
                    // Botões para cancelar e salvar
                    Row {
                        TextButton(
                            onClick = onDismiss
                        ) {
                            Text("Cancelar")
                        }
                        
                        TextButton(
                            onClick = {
                                if (medicationName.isNotBlank() && dosage.isNotBlank() && selectedTimes.isNotEmpty()) {
                                    val updatedMedication = Medication(
                                        name = medicationName,
                                        dosage = dosage,
                                        date = Date(), // Data atual
                                        times = selectedTimes
                                    )
                                    onConfirm(updatedMedication)
                                }
                            }
                        ) {
                            Text("Salvar")
                        }
                    }
                }
            }
        }
    }
    
    // Dialog para seleção de hora
    if (showTimePicker) {
        val currentTime = if (editingTimeIndex != null && editingTimeIndex!! < selectedTimes.size) {
            selectedTimes[editingTimeIndex!!]
        } else {
            val calendar = Calendar.getInstance()
            Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        }
        
        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.first,
            initialMinute = currentTime.second
        )
        
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecionar Hora",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    TimePicker(state = timePickerState)
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Cancelar")
                        }
                        TextButton(onClick = {
                            var selectedTime = Pair(timePickerState.hour, timePickerState.minute)
                            showTimePicker = false
                        }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreen() {
    // Formatadores de data e hora
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = { time: Pair<Int, Int> -> 
        "${time.first.toString().padStart(2, '0')}:${time.second.toString().padStart(2, '0')}"
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Histórico de Medicamentos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LightGreen,
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )
        
        // Mensagem informativa sobre a exclusão automática
        Text(
            text = "Os medicamentos são excluídos automaticamente após 7 dias",
            fontSize = 12.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
        )
        
        // Verificar se há medicamentos no histórico
        if (MedicationRepository.takenMedications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum medicamento no histórico.\nQuando você marcar medicamentos como tomados, eles aparecerão aqui.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            // Lista de medicamentos tomados
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(MedicationRepository.takenMedications.sortedByDescending { it.takenAt?.time ?: 0 }) { medication ->
                    MedicationCard(
                        medication = medication,
                        dateFormatter = dateFormatter,
                        timeFormatter = timeFormatter
                    )
                }
            }
        }
    }
}

@Composable
fun AccountScreen(
    onLogout: () -> Unit = {},
    onAccountDeleted: () -> Unit = {}
) {
    // Get current user from repository
    val user = UserRepository.getCurrentUser()
    
    // State for dialogs
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var showLogoutConfirmDialog by remember { mutableStateOf(false) }
    
    // State for password fields
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    // State for messages
    var passwordChangeSuccess by remember { mutableStateOf(false) }
    var passwordChangeError by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Conta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LightGreen,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )
        
        // User profile card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // User name
                Text(
                    text = "Nome",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = user?.name ?: "Nome não disponível",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // User email
                Text(
                    text = "Email",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = user?.email ?: "Email não disponível",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        // Account action buttons
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Change password button
                Button(
                    onClick = { showChangePasswordDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBlue
                    )
                ) {
                    Text(
                        text = "Mudar Password",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                
                // Delete account button
                Button(
                    onClick = { showDeleteAccountDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.8f)
                    )
                ) {
                    Text(
                        text = "Eliminar Conta",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                
                // Logout button
                Button(
                    onClick = { showLogoutConfirmDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Terminar Sessão",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
    
    // Change Password Dialog
    if (showChangePasswordDialog) {
        Dialog(onDismissRequest = { showChangePasswordDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Mudar Password",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    // Current password
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Password Atual", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
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
                    
                    // New password
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nova Password", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
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
                    
                    // Confirm new password
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar Nova Password", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
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
                    
                    // Success or error message
                    if (passwordChangeSuccess) {
                        Text(
                            text = "Password alterada com sucesso!",
                            color = LightGreen,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    
                    if (passwordChangeError) {
                        Text(
                            text = "Erro ao alterar a password. Verifique os dados inseridos.",
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    
                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showChangePasswordDialog = false }) {
                            Text("Cancelar")
                        }
                        TextButton(onClick = {
                            // Validate and change password
                            if (newPassword == confirmPassword && newPassword.isNotEmpty()) {
                                val success = UserRepository.changePassword(newPassword)
                                if (success) {
                                    passwordChangeSuccess = true
                                    passwordChangeError = false
                                    // Reset fields
                                    currentPassword = ""
                                    newPassword = ""
                                    confirmPassword = ""
                                    // Close dialog after a delay
                                    // In a real app, you would use a coroutine with delay
                                } else {
                                    passwordChangeError = true
                                    passwordChangeSuccess = false
                                }
                            } else {
                                passwordChangeError = true
                                passwordChangeSuccess = false
                            }
                        }) {
                            Text("Confirmar")
                        }
                    }
                }
            }
        }
    }
    
    // Delete Account Confirmation Dialog
    if (showDeleteAccountDialog) {
        Dialog(onDismissRequest = { showDeleteAccountDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Eliminar Conta",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Text(
                        text = "Tem certeza que deseja eliminar a sua conta? Esta ação não pode ser desfeita.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { showDeleteAccountDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text("Cancelar")
                        }
                        
                        Button(
                            onClick = {
                                UserRepository.deleteAccount()
                                showDeleteAccountDialog = false
                                onAccountDeleted()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            modifier = Modifier.weight(1f).padding(start = 8.dp)
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
    
    // Logout Confirmation Dialog
    if (showLogoutConfirmDialog) {
        Dialog(onDismissRequest = { showLogoutConfirmDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Terminar Sessão",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Text(
                        text = "Tem certeza que deseja terminar a sessão?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { showLogoutConfirmDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text("Cancelar")
                        }
                        
                        Button(
                            onClick = {
                                UserRepository.logout()
                                showLogoutConfirmDialog = false
                                onLogout()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightBlue
                            ),
                            modifier = Modifier.weight(1f).padding(start = 8.dp)
                        ) {
                            Text("Confirmar")
                        }
                    }
                }
            }
        }
    }
}