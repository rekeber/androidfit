package com.fitlife.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.fitlife.android.navigation.FitLifeNavigation
import com.fitlife.android.ui.theme.FitLifeTheme
import com.fitlife.android.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("MainActivity", "=== FitLife App Starting ===")
        enableEdgeToEdge()
        setContent {
            FitLifeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FitLifeApp()
                }
            }
        }
    }
}

@Composable
fun FitLifeApp() {
    android.util.Log.d("MainActivity", "FitLifeApp composable starting")
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    
    android.util.Log.d("MainActivity", "About to call FitLifeNavigation")
    FitLifeNavigation(
        navController = navController,
        authViewModel = authViewModel
    )
}

@Preview(showBackground = true)
@Composable
fun FitLifeAppPreview() {
    FitLifeTheme {
        FitLifeApp()
    }
}