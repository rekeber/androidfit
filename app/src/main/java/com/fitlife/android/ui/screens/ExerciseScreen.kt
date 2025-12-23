package com.fitlife.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Hoy", "Rutinas", "Ejercicios")
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        when (selectedTab) {
            0 -> TodayExerciseTab()
            1 -> RoutinesTab()
            2 -> ExercisesTab()
        }
    }
}

@Composable
fun TodayExerciseTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Resumen de actividad del día
            DailyActivitySummary()
        }
        
        item {
            // Rutina recomendada
            RecommendedWorkout()
        }
        
        item {
            // Ejercicios completados hoy
            TodayExercises()
        }
        
        item {
            // Estadísticas rápidas
            QuickExerciseStats()
        }
    }
}

@Composable
fun DailyActivitySummary() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Actividad de Hoy",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "250 calorías quemadas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
                
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(80.dp)
                ) {
                    CircularProgressIndicator(
                        progress = 0.62f,
                        modifier = Modifier.size(80.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        strokeWidth = 8.dp
                    )
                    Text(
                        text = "62%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActivityStat("Pasos", "8,432", "🚶")
                ActivityStat("Tiempo", "45 min", "⏱️")
                ActivityStat("Distancia", "6.2 km", "📍")
            }
        }
    }
}

@Composable
fun ActivityStat(label: String, value: String, emoji: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun RecommendedWorkout() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Rutina Recomendada",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                onClick = { /* Iniciar rutina */ }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💪",
                        fontSize = 40.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Entrenamiento de Fuerza",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "45 min • Nivel Intermedio",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "6 ejercicios • ~300 cal",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
                        )
                    }
                    
                    FilledTonalButton(
                        onClick = { /* Iniciar */ }
                    ) {
                        Text("Iniciar")
                    }
                }
            }
        }
    }
}

@Composable
fun TodayExercises() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ejercicios de Hoy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { /* Ver historial */ }) {
                    Text("Ver historial")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (true) { // Simulando que hay ejercicios
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ExerciseLogItem("Cardio", "30 min corriendo", "180 cal", true)
                    ExerciseLogItem("Fuerza", "3 series de flexiones", "70 cal", true)
                    ExerciseLogItem("Estiramiento", "15 min yoga", "25 cal", false)
                }
            } else {
                // Estado vacío
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏃‍♂️",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¡Hora de moverse!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Aún no has registrado ejercicios hoy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FilledTonalButton(
                        onClick = { /* Agregar ejercicio */ }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar Ejercicio")
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseLogItem(type: String, description: String, calories: String, completed: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonChecked,
            contentDescription = null,
            tint = if (completed) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = type,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        Text(
            text = calories,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun QuickExerciseStats() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Estadísticas de la Semana",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeekStat("Entrenamientos", "5", "de 6")
                WeekStat("Calorías", "1,250", "quemadas")
                WeekStat("Tiempo", "4.5h", "total")
            }
        }
    }
}

@Composable
fun WeekStat(label: String, value: String, subtitle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RoutinesTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Mis Rutinas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            // Rutinas rápidas
            Text(
                text = "Rutinas Rápidas",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    listOf(
                        QuickRoutine("Cardio", "15 min", "🏃‍♂️", Color(0xFFE91E63)),
                        QuickRoutine("Fuerza", "20 min", "💪", Color(0xFF2196F3)),
                        QuickRoutine("Yoga", "10 min", "🧘‍♀️", Color(0xFF4CAF50)),
                        QuickRoutine("HIIT", "12 min", "⚡", Color(0xFFFF9800))
                    )
                ) { routine ->
                    QuickRoutineCard(routine)
                }
            }
        }
        
        item {
            Text(
                text = "Rutinas Personalizadas",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(
            listOf(
                WorkoutRoutine("Entrenamiento Completo", "45 min", "8 ejercicios", "Intermedio"),
                WorkoutRoutine("Cardio Intenso", "30 min", "6 ejercicios", "Avanzado"),
                WorkoutRoutine("Fuerza para Principiantes", "25 min", "5 ejercicios", "Principiante"),
                WorkoutRoutine("Flexibilidad y Movilidad", "20 min", "7 ejercicios", "Todos los niveles")
            )
        ) { routine ->
            RoutineCard(routine)
        }
        
        item {
            // Botón para crear nueva rutina
            OutlinedButton(
                onClick = { /* Crear rutina */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Crear Nueva Rutina")
            }
        }
    }
}

@Composable
fun QuickRoutineCard(routine: QuickRoutine) {
    Card(
        modifier = Modifier.width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = routine.color.copy(alpha = 0.1f)
        ),
        onClick = { /* Iniciar rutina rápida */ }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = routine.emoji,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = routine.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = routine.duration,
                style = MaterialTheme.typography.bodySmall,
                color = routine.color
            )
        }
    }
}

@Composable
fun RoutineCard(routine: WorkoutRoutine) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Ver rutina */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = routine.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Row {
                    Text(
                        text = routine.duration,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " • ${routine.exercises} • ${routine.level}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            FilledTonalButton(
                onClick = { /* Iniciar rutina */ }
            ) {
                Text("Iniciar")
            }
        }
    }
}

@Composable
fun ExercisesTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Barra de búsqueda
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("Buscar ejercicios...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        item {
            // Categorías
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    listOf("Todos", "Cardio", "Fuerza", "Flexibilidad", "HIIT", "Yoga")
                ) { category ->
                    FilterChip(
                        onClick = { /* Filtrar por categoría */ },
                        label = { Text(category) },
                        selected = category == "Todos"
                    )
                }
            }
        }
        
        item {
            Text(
                text = "Ejercicios Populares",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(
            listOf(
                Exercise("Flexiones", "Pecho, Tríceps", "Principiante", "💪"),
                Exercise("Sentadillas", "Piernas, Glúteos", "Principiante", "🦵"),
                Exercise("Plancha", "Core", "Intermedio", "🏋️"),
                Exercise("Burpees", "Cuerpo completo", "Avanzado", "🔥"),
                Exercise("Dominadas", "Espalda, Bíceps", "Intermedio", "💪"),
                Exercise("Zancadas", "Piernas, Glúteos", "Principiante", "🦵")
            )
        ) { exercise ->
            ExerciseCard(exercise)
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Ver detalles del ejercicio */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = exercise.emoji,
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = exercise.muscles,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = exercise.level,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            IconButton(onClick = { /* Agregar a rutina */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}

data class QuickRoutine(
    val name: String,
    val duration: String,
    val emoji: String,
    val color: Color
)

data class WorkoutRoutine(
    val name: String,
    val duration: String,
    val exercises: String,
    val level: String
)

data class Exercise(
    val name: String,
    val muscles: String,
    val level: String,
    val emoji: String
)