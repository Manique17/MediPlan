package com.example.mediplan.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mediplan.RoomDB.MedicationHistoryData
import com.example.mediplan.UserDatabase
import com.example.mediplan.ViewModel.MedicationViewModel
import com.example.mediplan.ViewModel.Repository
import com.example.mediplan.ui.theme.LightBlue
import com.example.mediplan.ui.theme.LightGreen
import com.example.mediplan.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    userId: String,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val database = UserDatabase.getDatabase(context)
    val repository = Repository(database.dao)
    val viewModel = remember { MedicationViewModel(repository) }

    var selectedFilter by remember { mutableStateOf("ALL") }

    // Get history based on filter
    val allHistory by viewModel.getMedicationHistoryByUser(userId).observeAsState(emptyList())
    val takenHistory by viewModel.getMedicationHistoryByUserAndType(userId, "TAKEN").observeAsState(emptyList())
    val deletedHistory by viewModel.getMedicationHistoryByUserAndType(userId, "DELETED").observeAsState(emptyList())
    val completedHistory by viewModel.getMedicationHistoryByUserAndType(userId, "COMPLETED").observeAsState(emptyList())

    val displayHistory = when (selectedFilter) {
        "TAKEN" -> takenHistory
        "DELETED" -> deletedHistory
        "COMPLETED" -> completedHistory
        else -> allHistory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Histórico de Medicamentos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = LightGreen
            )
        )

        // Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                onClick = { selectedFilter = "ALL" },
                label = { Text("Todos") },
                selected = selectedFilter == "ALL",
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LightGreen,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                onClick = { selectedFilter = "TAKEN" },
                label = { Text("Tomados") },
                selected = selectedFilter == "TAKEN",
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LightGreen,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                onClick = { selectedFilter = "COMPLETED" },
                label = { Text("Concluídos") },
                selected = selectedFilter == "COMPLETED",
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LightGreen,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                onClick = { selectedFilter = "DELETED" },
                label = { Text("Removidos") },
                selected = selectedFilter == "DELETED",
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LightGreen,
                    selectedLabelColor = Color.White
                )
            )
        }

        // History List
        if (displayHistory.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.MedicalServices,
                        contentDescription = "Sem histórico",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = when (selectedFilter) {
                            "TAKEN" -> "Nenhum medicamento tomado ainda"
                            "DELETED" -> "Nenhum medicamento removido"
                            "COMPLETED" -> "Nenhum tratamento concluído"
                            else -> "Nenhum histórico encontrado"
                        },
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(displayHistory) { historyItem ->
                    HistoryCard(historyItem = historyItem, onDelete = { item ->
                        viewModel.deleteHistoryItem(item)
                    })
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}




@Composable
fun HistoryCard(historyItem: MedicationHistoryData, onDelete: (MedicationHistoryData) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = historyItem.medName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onDelete(historyItem) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when (historyItem.action) {
                    "tomado" -> "Medicamento tomado"
                    "completed" -> "Tratamento concluído"
                    "removed" -> "Medicamento removido"
                    else -> historyItem.action
                },
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Data: ${historyItem.actionDate}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(userId = "test-user-id")
}