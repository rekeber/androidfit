package com.fitlife.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mensajes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* Nuevo mensaje */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Nuevo mensaje")
                }
            }
        }
        
        item {
            // Barra de búsqueda
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("Buscar conversaciones...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        items(
            listOf(
                Conversation("María González", "👩‍🦰", "¡Genial entrenamiento hoy! 💪", "2 min", true, 2),
                Conversation("Carlos Ruiz", "👨‍💼", "¿Vamos a correr mañana?", "15 min", false, 0),
                Conversation("Ana López", "👩‍🎓", "Gracias por la receta saludable", "1h", false, 0),
                Conversation("Diego Martín", "👨‍🏫", "¿Cómo va tu racha?", "2h", false, 1),
                Conversation("Laura Pérez", "👩‍⚕️", "Te envié mi rutina de yoga", "1d", false, 0),
                Conversation("Miguel Torres", "👨‍🔧", "¡Felicidades por tu logro!", "2d", false, 0),
                Conversation("Grupo: Runners", "🏃‍♂️", "Roberto: ¿Alguien para el 5K?", "3d", false, 5)
            )
        ) { conversation ->
            ConversationCard(conversation)
        }
    }
}

@Composable
fun ConversationCard(conversation: Conversation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Abrir conversación */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.avatar,
                    fontSize = 28.sp
                )
                
                // Indicador de en línea
                if (conversation.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "En línea",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = conversation.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.Medium
                    )
                    Text(
                        text = conversation.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = conversation.lastMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (conversation.unreadCount > 0) 
                            MaterialTheme.colorScheme.onSurface 
                        else 
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )
                    
                    if (conversation.unreadCount > 0) {
                        Badge {
                            Text(
                                text = conversation.unreadCount.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Conversation(
    val name: String,
    val avatar: String,
    val lastMessage: String,
    val time: String,
    val isOnline: Boolean,
    val unreadCount: Int
)