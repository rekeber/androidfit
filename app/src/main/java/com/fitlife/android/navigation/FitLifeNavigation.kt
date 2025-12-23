package com.fitlife.android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fitlife.android.ui.screens.DashboardScreen
import com.fitlife.android.ui.screens.ExerciseScreen
import com.fitlife.android.ui.screens.NutritionScreen
import com.fitlife.android.ui.screens.SocialScreen
import com.fitlife.android.ui.screens.ProfileScreen
import com.fitlife.android.ui.screens.LoginScreen
import com.fitlife.android.ui.screens.RegisterScreen
import com.fitlife.android.ui.screens.MessagesScreen
import com.fitlife.android.viewmodel.AuthViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Filled.Home)
    object Exercise : Screen("exercise", "Ejercicios", Icons.Filled.FitnessCenter)
    object Nutrition : Screen("nutrition", "Nutrición", Icons.Filled.Restaurant)
    object Social : Screen("social", "Social", Icons.Filled.People)
    object Messages : Screen("messages", "Mensajes", Icons.Filled.Message)
    object Profile : Screen("profile", "Perfil", Icons.Filled.Person)
    
    // Auth screens
    object Login : Screen("login", "Iniciar Sesión", Icons.Filled.Login)
    object Register : Screen("register", "Registrarse", Icons.Filled.PersonAdd)
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Exercise,
    Screen.Nutrition,
    Screen.Social,
    Screen.Messages,
    Screen.Profile
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitLifeNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    
    if (!isAuthenticated) {
        // Auth Navigation
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                    onLoginSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            
            composable(Screen.Register.route) {
                RegisterScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    },
                    onRegisterSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    } else {
        // Main App Navigation with Bottom Navigation
        Scaffold(
            bottomBar = {
                FitLifeBottomNavigation(navController = navController)
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Dashboard.route) {
                    DashboardScreen()
                }
                
                composable(Screen.Exercise.route) {
                    ExerciseScreen()
                }
                
                composable(Screen.Nutrition.route) {
                    NutritionScreen()
                }
                
                composable(Screen.Social.route) {
                    SocialScreen()
                }
                
                composable(Screen.Messages.route) {
                    MessagesScreen()
                }
                
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun FitLifeBottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { 
                    if (screen == Screen.Messages) {
                        BadgedBox(
                            badge = { Badge { Text("3") } }
                        ) {
                            Icon(screen.icon, contentDescription = screen.title)
                        }
                    } else {
                        Icon(screen.icon, contentDescription = screen.title)
                    }
                },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}