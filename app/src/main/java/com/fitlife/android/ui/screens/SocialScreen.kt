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
fun SocialScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Feed", "Amigos", "Logros")
    
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
            0 -> FeedTab()
            1 -> FriendsTab()
            2 -> AchievementsTab()
        }
    }
}

@Composable
fun FeedTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Crear post
            CreatePostCard()
        }
        
        items(
            listOf(
                SocialPost(
                    userName = "María González",
                    userAvatar = "👩‍🦰",
                    timeAgo = "2h",
                    content = "¡Completé mi primera rutina de 5K! 🏃‍♀️ Me siento increíble",
                    type = PostType.ACHIEVEMENT,
                    likes = 24,
                    comments = 8,
                    achievement = "Primera carrera de 5K"
                ),
                SocialPost(
                    userName = "Carlos Ruiz",
                    userAvatar = "👨‍💼",
                    timeAgo = "4h",
                    content = "Desayuno saludable para empezar el día con energía 💪",
                    type = PostType.NUTRITION,
                    likes = 15,
                    comments = 3,
                    foodImage = "🥗"
                ),
                SocialPost(
                    userName = "Ana López",
                    userAvatar = "👩‍🎓",
                    timeAgo = "6h",
                    content = "30 días seguidos registrando mis comidas. ¡La constancia da resultados!",
                    type = PostType.MILESTONE,
                    likes = 42,
                    comments = 12,
                    streak = 30
                ),
                SocialPost(
                    userName = "Diego Martín",
                    userAvatar = "👨‍🏫",
                    timeAgo = "8h",
                    content = "Nueva rutina de fuerza completada. Cada día más fuerte 💪",
                    type = PostType.WORKOUT,
                    likes = 18,
                    comments = 5,
                    workoutName = "Fuerza Superior"
                )
            )
        ) { post ->
            SocialPostCard(post)
        }
    }
}

@Composable
fun CreatePostCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar del usuario
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    fontSize = 24.sp
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text("¿Qué has logrado hoy?") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(onClick = { /* Crear post */ }) {
                Icon(Icons.Default.Send, contentDescription = "Publicar")
            }
        }
    }
}

@Composable
fun SocialPostCard(post: SocialPost) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header del post
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.userAvatar,
                        fontSize = 24.sp
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = post.userName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.timeAgo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                IconButton(onClick = { /* Más opciones */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Más")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Contenido del post
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium
            )
            
            // Contenido específico según el tipo
            when (post.type) {
                PostType.ACHIEVEMENT -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🏆", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Logro desbloqueado: ${post.achievement}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFFF8F00)
                            )
                        }
                    }
                }
                PostType.NUTRITION -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = post.foodImage ?: "🍽️", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Comida registrada",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                PostType.MILESTONE -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🔥", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${post.streak} días de racha",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                PostType.WORKOUT -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = post.workoutName ?: "Entrenamiento completado",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Acciones del post
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PostAction(
                    icon = Icons.Default.FavoriteBorder,
                    text = "${post.likes}",
                    onClick = { /* Like */ }
                )
                PostAction(
                    icon = Icons.Default.ChatBubble,
                    text = "${post.comments}",
                    onClick = { /* Comentar */ }
                )
                PostAction(
                    icon = Icons.Default.Share,
                    text = "Compartir",
                    onClick = { /* Compartir */ }
                )
            }
        }
    }
}

@Composable
fun PostAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun FriendsTab() {
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
                placeholder = { Text("Buscar amigos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mis Amigos (12)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { /* Invitar amigos */ }) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Invitar")
                }
            }
        }
        
        items(
            listOf(
                Friend("María González", "👩‍🦰", "Activa hace 2h", true, 15),
                Friend("Carlos Ruiz", "👨‍💼", "Activa hace 1d", false, 8),
                Friend("Ana López", "👩‍🎓", "Activa hace 3h", true, 30),
                Friend("Diego Martín", "👨‍🏫", "Activa hace 5h", true, 12),
                Friend("Laura Pérez", "👩‍⚕️", "Activa hace 2d", false, 22),
                Friend("Miguel Torres", "👨‍🔧", "Activa hace 1h", true, 7)
            )
        ) { friend ->
            FriendCard(friend)
        }
        
        item {
            Text(
                text = "Solicitudes de Amistad (2)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(
            listOf(
                FriendRequest("Roberto Silva", "👨‍🎨", "Quiere ser tu amigo"),
                FriendRequest("Carmen Vega", "👩‍🍳", "Quiere ser tu amigo")
            )
        ) { request ->
            FriendRequestCard(request)
        }
    }
}

@Composable
fun FriendCard(friend: Friend) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Ver perfil del amigo */ }
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
                    text = friend.avatar,
                    fontSize = 28.sp
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = friend.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    if (friend.isOnline) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = "En línea",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(8.dp)
                            )
                        }
                    }
                }
                Text(
                    text = friend.lastActive,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "${friend.streak} días de racha",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            IconButton(onClick = { /* Enviar mensaje */ }) {
                Icon(Icons.Default.Message, contentDescription = "Mensaje")
            }
        }
    }
}

@Composable
fun FriendRequestCard(request: FriendRequest) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = request.avatar,
                    fontSize = 24.sp
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = request.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = request.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            Row {
                IconButton(onClick = { /* Aceptar */ }) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Aceptar",
                        tint = Color(0xFF4CAF50)
                    )
                }
                IconButton(onClick = { /* Rechazar */ }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Rechazar",
                        tint = Color(0xFFF44336)
                    )
                }
            }
        }
    }
}

@Composable
fun AchievementsTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Resumen de logros
            AchievementsSummary()
        }
        
        item {
            Text(
                text = "Logros Recientes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(
            listOf(
                Achievement("Primera Carrera 5K", "Completaste tu primera carrera de 5 kilómetros", "🏃‍♂️", true, "Hace 2 días"),
                Achievement("Racha de 30 Días", "30 días consecutivos registrando comidas", "🔥", true, "Hace 1 semana"),
                Achievement("Maestro de Proteínas", "Alcanzaste tu meta de proteínas 7 días seguidos", "💪", true, "Hace 2 semanas"),
                Achievement("Hidratación Perfecta", "Bebiste 8 vasos de agua por 5 días consecutivos", "💧", false, "En progreso"),
                Achievement("Guerrero del Gimnasio", "Completaste 50 entrenamientos", "🏋️‍♂️", false, "40/50 completados"),
                Achievement("Chef Saludable", "Cocinaste 25 recetas saludables", "👨‍🍳", false, "18/25 completadas")
            )
        ) { achievement ->
            AchievementCard(achievement)
        }
    }
}

@Composable
fun AchievementsSummary() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Mis Logros",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AchievementStat("Desbloqueados", "8", "🏆")
                AchievementStat("En Progreso", "4", "⏳")
                AchievementStat("Puntos Totales", "1,250", "⭐")
            }
        }
    }
}

@Composable
fun AchievementStat(label: String, value: String, emoji: String) {
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
fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.unlocked) 
                MaterialTheme.colorScheme.surfaceVariant 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = achievement.icon,
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (achievement.unlocked) 
                        MaterialTheme.colorScheme.onSurface 
                    else 
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = achievement.status,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (achievement.unlocked) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            
            if (achievement.unlocked) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Desbloqueado",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Data classes
data class SocialPost(
    val userName: String,
    val userAvatar: String,
    val timeAgo: String,
    val content: String,
    val type: PostType,
    val likes: Int,
    val comments: Int,
    val achievement: String? = null,
    val foodImage: String? = null,
    val streak: Int? = null,
    val workoutName: String? = null
)

enum class PostType {
    ACHIEVEMENT, NUTRITION, MILESTONE, WORKOUT
}

data class Friend(
    val name: String,
    val avatar: String,
    val lastActive: String,
    val isOnline: Boolean,
    val streak: Int
)

data class FriendRequest(
    val name: String,
    val avatar: String,
    val message: String
)

data class Achievement(
    val title: String,
    val description: String,
    val icon: String,
    val unlocked: Boolean,
    val status: String
)