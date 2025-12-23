package com.fitlife.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun NutritionScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Hoy", "Alimentos", "Recetas")
    
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
            0 -> TodayNutritionTab()
            1 -> FoodsTab()
            2 -> RecipesTab()
        }
    }
}

@Composable
fun TodayNutritionTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Resumen de calorías del día
            DailyCaloriesSummary()
        }
        
        item {
            // Distribución de macronutrientes
            MacronutrientDistribution()
        }
        
        item {
            // Comidas del día
            MealsSection()
        }
        
        item {
            // Hidratación
            HydrationSection()
        }
    }
}

@Composable
fun DailyCaloriesSummary() {
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
                        text = "Calorías Consumidas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "1,450 de 2,000 cal",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
                
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(80.dp)
                ) {
                    CircularProgressIndicator(
                        progress = 0.725f,
                        modifier = Modifier.size(80.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        strokeWidth = 8.dp
                    )
                    Text(
                        text = "72%",
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
                CalorieBreakdown("Desayuno", "420", Color(0xFF4CAF50))
                CalorieBreakdown("Almuerzo", "580", Color(0xFF2196F3))
                CalorieBreakdown("Cena", "450", Color(0xFFFF9800))
                CalorieBreakdown("Snacks", "0", Color(0xFF9E9E9E))
            }
        }
    }
}

@Composable
fun CalorieBreakdown(meal: String, calories: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = calories,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = meal,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun MacronutrientDistribution() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Distribución de Macronutrientes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MacroProgressBar("Proteínas", 85, 120, Color(0xFFE91E63))
                MacroProgressBar("Carbohidratos", 180, 250, Color(0xFF2196F3))
                MacroProgressBar("Grasas", 45, 67, Color(0xFFFF9800))
                MacroProgressBar("Fibra", 18, 25, Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
fun MacroProgressBar(name: String, current: Int, target: Int, color: Color) {
    val progress = (current.toFloat() / target.toFloat()).coerceAtMost(1f)
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${current}g / ${target}g",
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun MealsSection() {
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
                    text = "Comidas de Hoy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* Agregar comida */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar comida")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MealItem("Desayuno", "Avena con frutas y nueces", "420 cal", Icons.Default.LightMode)
                MealItem("Almuerzo", "Pollo a la plancha con ensalada", "580 cal", Icons.Default.Restaurant)
                MealItem("Cena", "Salmón con verduras al vapor", "450 cal", Icons.Default.Restaurant)
                
                // Botón para agregar snack
                OutlinedButton(
                    onClick = { /* Agregar snack */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar Snack")
                }
            }
        }
    }
}

@Composable
fun MealItem(
    mealType: String,
    description: String,
    calories: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
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
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = mealType,
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
fun HydrationSection() {
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
                    text = "Hidratación",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* Agregar vaso */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar vaso")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "6 de 8 vasos",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "1.5L / 2L",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = 0.75f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF2196F3),
                trackColor = Color(0xFF2196F3).copy(alpha = 0.2f)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Vasos de agua visual
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(8) { index ->
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = if (index < 6) Color(0xFF2196F3) else Color(0xFF9E9E9E),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FoodsTab() {
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
                placeholder = { Text("Buscar alimentos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        item {
            Text(
                text = "Alimentos Populares",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(
            listOf(
                FoodItem("Manzana", "52 cal/100g", "Rica en fibra y vitaminas"),
                FoodItem("Pollo (pechuga)", "165 cal/100g", "Alto en proteínas"),
                FoodItem("Arroz integral", "111 cal/100g", "Carbohidrato complejo"),
                FoodItem("Brócoli", "34 cal/100g", "Rico en vitaminas y minerales"),
                FoodItem("Salmón", "208 cal/100g", "Rico en omega-3"),
                FoodItem("Avena", "389 cal/100g", "Rica en fibra soluble")
            )
        ) { food ->
            FoodCard(food)
        }
    }
}

@Composable
fun FoodCard(food: FoodItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Ver detalles del alimento */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = food.calories,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = food.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            IconButton(onClick = { /* Agregar a comida */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}

@Composable
fun RecipesTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Recetas Saludables",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(
            listOf(
                RecipeItem("Ensalada de Quinoa", "320 cal", "25 min", "🥗"),
                RecipeItem("Smoothie Verde", "180 cal", "5 min", "🥤"),
                RecipeItem("Pollo al Horno", "280 cal", "45 min", "🍗"),
                RecipeItem("Bowl de Acai", "250 cal", "10 min", "🍓")
            )
        ) { recipe ->
            RecipeCard(recipe)
        }
    }
}

@Composable
fun RecipeCard(recipe: RecipeItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Ver receta completa */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recipe.emoji,
                fontSize = 40.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Row {
                    Text(
                        text = recipe.calories,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Text(
                        text = recipe.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

data class FoodItem(
    val name: String,
    val calories: String,
    val description: String
)

data class RecipeItem(
    val name: String,
    val calories: String,
    val time: String,
    val emoji: String
)