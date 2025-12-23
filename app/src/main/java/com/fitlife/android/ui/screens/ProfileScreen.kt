package com.fitlife.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header del perfil
            ProfileHeader()
        }
        
        item {
            // Estadísticas del usuario
            UserStats()
        }
        
        item {
            // Progreso y objetivos
            ProgressSection()
        }
        
        item {
            // Configuración rápida
            QuickSettings()
        }
        
        item {
            // Opciones del perfil
            ProfileOptions()
        }
    }
}

@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar del usuario
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    fontSize = 60.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Usuario FitLife",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Text(
                text = "usuario@fitlife.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilledTonalButton(
                    onClick = { /* Editar perfil */ }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Editar Perfil")
                }
                
                OutlinedButton(
                    onClick = { /* Compartir perfil */ }
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compartir")
                }
            }
        }
    }
}

@Composable
fun UserStats() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Mis Estadísticas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("Peso Actual", "72.5 kg", Color(0xFF4CAF50))
                StatItem("IMC", "22.1", Color(0xFF2196F3))
                StatItem("Racha", "15 días", Color(0xFFFF9800))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("Entrenamientos", "45", Color(0xFF9C27B0))
                StatItem("Calorías Quemadas", "12,450", Color(0xFFE91E63))
                StatItem("Puntos", "1,250", Color(0xFFFFD700))
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ProgressSection() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Mi Progreso",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progreso de peso
            ProgressItem(
                title = "Objetivo de Peso",
                current = "72.5 kg",
                target = "70.0 kg",
                progress = 0.7f,
                color = Color(0xFF4CAF50)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Progreso de entrenamientos semanales
            ProgressItem(
                title = "Entrenamientos Semanales",
                current = "5",
                target = "6",
                progress = 0.83f,
                color = Color(0xFF2196F3)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Progreso de calorías diarias
            ProgressItem(
                title = "Meta Calórica Diaria",
                current = "1,450",
                target = "2,000",
                progress = 0.725f,
                color = Color(0xFFFF9800)
            )
        }
    }
}

@Composable
fun ProgressItem(
    title: String,
    current: String,
    target: String,
    progress: Float,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "$current / $target",
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun QuickSettings() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Configuración Rápida",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Notificaciones
            SettingItem(
                icon = Icons.Default.Notifications,
                title = "Notificaciones",
                subtitle = "Recordatorios y alertas",
                hasSwitch = true,
                switchState = true,
                onSwitchChange = { /* Toggle notificaciones */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Modo privado
            SettingItem(
                icon = Icons.Default.Lock,
                title = "Perfil Privado",
                subtitle = "Solo amigos pueden ver tu actividad",
                hasSwitch = true,
                switchState = false,
                onSwitchChange = { /* Toggle privacidad */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Sincronización
            SettingItem(
                icon = Icons.Default.Sync,
                title = "Sincronización Automática",
                subtitle = "Sincronizar datos con otros dispositivos",
                hasSwitch = true,
                switchState = true,
                onSwitchChange = { /* Toggle sync */ }
            )
        }
    }
}

@Composable
fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    hasSwitch: Boolean = false,
    switchState: Boolean = false,
    onSwitchChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        if (hasSwitch) {
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchChange
            )
        } else {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun ProfileOptions() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Opciones",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Datos personales
            ProfileOptionItem(
                icon = Icons.Default.Person,
                title = "Datos Personales",
                subtitle = "Edad, altura, peso objetivo",
                onClick = { /* Abrir datos personales */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Objetivos
            ProfileOptionItem(
                icon = Icons.Default.Flag,
                title = "Mis Objetivos",
                subtitle = "Configurar metas de peso y actividad",
                onClick = { /* Abrir objetivos */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Preferencias alimentarias
            ProfileOptionItem(
                icon = Icons.Default.Restaurant,
                title = "Preferencias Alimentarias",
                subtitle = "Alergias y restricciones dietéticas",
                onClick = { /* Abrir preferencias */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Historial médico
            ProfileOptionItem(
                icon = Icons.Default.LocalHospital,
                title = "Historial Médico",
                subtitle = "Condiciones médicas y medicamentos",
                onClick = { /* Abrir historial */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Exportar datos
            ProfileOptionItem(
                icon = Icons.Default.Download,
                title = "Exportar Datos",
                subtitle = "Descargar tu información personal",
                onClick = { /* Exportar datos */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Ayuda y soporte
            ProfileOptionItem(
                icon = Icons.Default.Help,
                title = "Ayuda y Soporte",
                subtitle = "FAQ, contacto y tutoriales",
                onClick = { /* Abrir ayuda */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Configuración avanzada
            ProfileOptionItem(
                icon = Icons.Default.Settings,
                title = "Configuración Avanzada",
                subtitle = "Unidades, idioma, tema",
                onClick = { /* Abrir configuración */ }
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Cerrar sesión
            OutlinedButton(
                onClick = { /* Cerrar sesión */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFF44336)
                )
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }
    }
}

@Composable
fun ProfileOptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}