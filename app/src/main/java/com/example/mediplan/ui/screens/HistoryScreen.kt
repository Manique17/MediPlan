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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
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
import com.example.mediplan.ui.theme.LightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    userId: String, // ID do utilizador para filtrar o histórico
    onBackClick: () -> Unit = {} // Callback para o botão de voltar
) {
    // Obtém o contexto local.
    val context = LocalContext.current
    // Configuração da base de dados, repositório e ViewModel.
    val database = UserDatabase.getDatabase(context)
    val repository = Repository(database.dao)
    val viewModel = remember { MedicationViewModel(repository) }

    // Estado para o filtro selecionado (ex: "TODOS", "Tomado", "Removido").
    var selectedFilter by remember { mutableStateOf("ALL") } // Valor inicial do filtro

    // Observa o histórico de medicação do ViewModel, filtrado pelo utilizador e tipo de ação.
    val allHistory by viewModel.getMedicationHistoryByUser(userId).observeAsState(emptyList())
    // Nota: "TAKEN" está em inglês, enquanto os outros filtros estão em português.
    // Seria bom padronizar os valores de `actionType` no ViewModel e na base de dados.
    val takenHistory by viewModel.getMedicationHistoryByUserAndType(userId, "TAKEN").observeAsState(emptyList())
    val deletedHistory by viewModel.getMedicationHistoryByUserAndType(userId, "Eliminado").observeAsState(emptyList())
    val completedHistory by viewModel.getMedicationHistoryByUserAndType(userId, "Completado").observeAsState(emptyList())

    // Determina qual lista de histórico exibir com base no filtro selecionado.
    val displayHistory = when (selectedFilter) {
        "Tomado" -> takenHistory       // Se o filtro for "Tomado", mostra `takenHistory`.
        "Removido" -> deletedHistory   // Se o filtro for "Removido", mostra `deletedHistory`.
        "Concluido" -> completedHistory// Se o filtro for "Concluido", mostra `completedHistory`.
        else -> allHistory             // Por defeito (ou "ALL"), mostra `allHistory`.
    }

    // Coluna principal que ocupa todo o ecrã.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Cor de fundo cinza claro.
    ) {
        // Barra de topo da aplicação.
        TopAppBar(
            title = {
                Text(
                    text = "Histórico de Medicamentos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Cor do texto do título.
                )
            },
            navigationIcon = {
                // Botão de voltar.
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White // Cor do ícone de voltar.
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = LightGreen // Cor de fundo da barra de topo.
            )
        )

        // Linha para os chips de filtro (atualmente vazia).
        // TODO: Implementar FilterChips aqui para permitir ao utilizador mudar `selectedFilter`.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding para os filtros.
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os filtros.
        ) {
            // Aqui seriam adicionados os FilterChip ou botões para mudar o `selectedFilter`.
            // Exemplo:
            // Button(onClick = { selectedFilter = "ALL" }) { Text("Todos") }
            // Button(onClick = { selectedFilter = "Tomado" }) { Text("Tomados") }
            // ... e assim por diante.
        }

        // Lista de histórico ou mensagem de "sem histórico".
        if (displayHistory.isEmpty()) {
            // Caixa para centralizar a mensagem quando não há histórico.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp), // Padding para a mensagem.
                contentAlignment = Alignment.Center // Centraliza o conteúdo.
            ) {
                // Coluna para o ícone e texto da mensagem.
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally // Centraliza horizontalmente.
                ) {
                    Icon(
                        Icons.Default.MedicalServices,
                        contentDescription = "Sem histórico",
                        modifier = Modifier.size(64.dp), // Tamanho do ícone.
                        tint = Color.Gray // Cor do ícone.
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Espaçador vertical.
                    // Texto dinâmico baseado no filtro selecionado.
                    Text(
                        text = when (selectedFilter) {
                            "TAKEN" -> "Nenhum medicamento tomado ainda" // Mensagem para filtro "TAKEN".
                            "Eliminado" -> "Nenhum medicamento removido"  // Mensagem para filtro "Eliminado".
                            "Completado" -> "Nenhum tratamento concluído" // Mensagem para filtro "Completado".
                            else -> "Nenhum histórico encontrado"        // Mensagem padrão.
                        },
                        fontSize = 16.sp,
                        color = Color.Gray, // Cor do texto.
                        textAlign = TextAlign.Center // Texto centralizado.
                    )
                }
            }
        } else {
            // LazyColumn para exibir a lista de itens de histórico de forma eficiente.
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp), // Padding horizontal para a lista.
                verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento vertical entre os cartões.
            ) {
                // Itera sobre a lista `displayHistory` e cria um `HistoryCard` para cada item.
                items(displayHistory) { historyItem ->
                    HistoryCard(
                        historyItem = historyItem,
                        onDelete = { item -> // Callback para eliminar um item do histórico.
                            viewModel.deleteHistoryItem(item)
                        }
                    )
                }
                // Adiciona um espaçador no final da lista.
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// Composable para exibir um único cartão de item de histórico.
@Composable
fun HistoryCard(
    historyItem: MedicationHistoryData, // Dados do item de histórico.
    onDelete: (MedicationHistoryData) -> Unit = {} // Callback para o botão de eliminar.
) {
    // Cartão para exibir as informações do item de histórico.
    Card(
        modifier = Modifier.fillMaxWidth(), // Ocupa a largura máxima.
        colors = CardDefaults.cardColors(containerColor = Color.White), // Cor de fundo do cartão.
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Sombra do cartão.
    ) {
        // Coluna para organizar o conteúdo dentro do cartão.
        Column(modifier = Modifier.padding(16.dp)) { // Padding interno do cartão.
            // Linha para o nome do medicamento e o botão de eliminar.
            Row(verticalAlignment = Alignment.CenterVertically) { // Alinha verticalmente os itens.
                Text(
                    text = historyItem.medName, // Nome do medicamento.
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray, // Cor do nome do medicamento.
                    modifier = Modifier.weight(1f) // Ocupa o espaço restante.
                )
                // Botão para eliminar o item de histórico.
                IconButton(onClick = { onDelete(historyItem) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red // Cor do ícone de eliminar.
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp)) // Espaçador vertical pequeno.
            // Texto para a ação realizada (ex: "Medicamento tomado").
            Text(
                text = when (historyItem.action) { // Texto dinâmico baseado no tipo de ação.
                    "tomado" -> "Medicamento tomado"
                    "Completado" -> "Tratamento concluído"
                    "Eliminado" -> "Medicamento removido"
                    else -> historyItem.action // Mostra a ação como está, se não for um dos casos acima.
                },
                fontSize = 14.sp,
                color = Color.Gray // Cor do texto da ação.
            )
            Spacer(modifier = Modifier.height(4.dp)) // Espaçador vertical pequeno.
            // Texto para a data da ação.
            Text(
                text = "Data: ${historyItem.actionDate}", // Data da ação.
                fontSize = 12.sp,
                color = Color.Gray // Cor do texto da data.
            )
        }
    }
}

// Pré-visualização Composable para o ecrã de histórico.
@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    // Chama o HistoryScreen com um ID de utilizador de teste para a pré-visualização.
    HistoryScreen(userId = "test-user-id")
}
