package com.fitlife.android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fitlife.android.ui.screens.DashboardScreen
import com.fitlife.android.viewmodel.AuthViewModel
import com.fitlife.android.ui.screens.ExerciseScreen
import com.fitlife.android.ui.screens.NutritionScreen
import com.fitlife.android.ui.screens.SocialScreen
import com.fitlife.android.ui.screens.ProfileScreen
import com.fitlife.android.ui.screens.LoginScreen
import com.fitlife.android.ui.screens.RegisterScreen
import com.fitlife.android.ui.screens.MessagesScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Filled.Home)
    object Exercise : Screen("exercise", "Ejercicios", Icons.Filled.FitnessCenter)
    object Nutrition : Screen("nutrition", "Nutrición", Icons.Filled.Restaurant)
    object Social : Screen("social", "Social", Icons.Filled.People)
    object Messages : Screen("messages", "Mensajes", Icons.Filled.Email)
    object Profile : Screen("profile", "Perfil", Icons.Filled.Person)
    
    // Auth screens
    object Login : Screen("login", "Iniciar Sesión", Icons.Filled.AccountCircle)
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
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by authViewModel.uiState.collectAsStateWithLifecycle()
    
    android.util.Log.d("FitLifeNavigation", "=== Navigation Recomposition ===")
    android.util.Log.d("FitLifeNavigation", "isLoggedIn: ${uiState.isLoggedIn}")
    android.util.Log.d("FitLifeNavigation", "currentUserName: ${uiState.currentUserName}")
    android.util.Log.d("FitLifeNavigation", "currentUserEmail: ${uiState.currentUserEmail}")
    
    // SIMPLIFIED: Direct navigation based on login state changes
    LaunchedEffect(uiState.isLoggedIn) {
        android.util.Log.d("FitLifeNavigation", "=== LOGIN STATE CHANGED ===")
        android.util.Log.d("FitLifeNavigation", "New login state: ${uiState.isLoggedIn}")
        
        try {
            // Small delay to ensure state is stable
            kotlinx.coroutines.delay(50)
            
            if (uiState.isLoggedIn) {
                android.util.Log.d("FitLifeNavigation", "Navigating to Dashboard due to login")
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
                android.util.Log.d("FitLifeNavigation", "Navigation to Dashboard completed")
            } else {
                android.util.Log.d("FitLifeNavigation", "Navigating to Login due to logout")
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
                android.util.Log.d("FitLifeNavigation", "Navigation to Login completed")
            }
        } catch (e: Exception) {
            android.util.Log.e("FitLifeNavigation", "Error during state-based navigation", e)
        }
    }
    
    // Single NavHost with all screens - key changes when login state changes to force recreation
    NavHost(
        navController = navController,
        startDestination = if (uiState.isLoggedIn) Screen.Dashboard.route else Screen.Login.route,
        modifier = Modifier.fillMaxSize()
    ) {
        // Auth screens
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    android.util.Log.d("FitLifeNavigation", "=== LOGIN SUCCESS CALLBACK ===")
                    android.util.Log.d("FitLifeNavigation", "Login success callback received - state should handle navigation")
                    // Let the LaunchedEffect handle navigation based on state change
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterSuccess = {
                    android.util.Log.d("FitLifeNavigation", "=== REGISTER SUCCESS CALLBACK ===")
                    android.util.Log.d("FitLifeNavigation", "Register success callback received - state should handle navigation")
                    // Let the LaunchedEffect handle navigation based on state change
                }
            )
        }
        
        // Main app screens with conditional bottom navigation
        composable(Screen.Dashboard.route) {
            if (uiState.isLoggedIn) {
                MainAppWithBottomNav(navController = navController) {
                    DashboardScreen()
                }
            } else {
                // Redirect to login if not logged in
                LaunchedEffect(Unit) {
                    android.util.Log.d("FitLifeNavigation", "Dashboard accessed without login - redirecting")
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            }
        }
        
        composable(Screen.Exercise.route) {
            if (uiState.isLoggedIn) {
                MainAppWithBottomNav(navController = navController) {
                    ExerciseScreen()
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Exercise.route) { inclusive = true }
                    }
                }
            }
        }
        
        composable(Screen.Nutrition.route) {
            if (uiState.isLoggedIn) {
                MainAppWithBottomNav(navController = navController) {
                    NutritionScreen()
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Nutrition.route) { inclusive = true }
                    }
                }
            }
        }
        
        composable(Screen.Social.route) {
            if (uiState.isLoggedIn) {
                MainAppWithBottomNav(navController = navController) {
                    SocialScreen()
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Social.route) { inclusive = true }
                    }
                }
            }
        }
        
        composable(Screen.Messages.route) {
            if (uiState.isLoggedIn) {
                MainAppWithBottomNav(navController = navController) {
                    MessagesScreen()
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Messages.route) { inclusive = true }
                    }
                }
            }
        }
        
        composable(Screen.Profile.route) {
            if (uiState.isLoggedIn) {
                MainAppWithBottomNav(navController = navController) {
                    ProfileScreen(authViewModel = authViewModel)
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
fun MainAppWithBottomNav(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            FitLifeBottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            content()
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