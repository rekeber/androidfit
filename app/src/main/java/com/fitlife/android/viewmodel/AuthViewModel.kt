package com.fitlife.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlife.android.data.model.AuthResponse
import com.fitlife.android.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()
    
    init {
        checkLoginStatus()
    }
    
    private fun checkLoginStatus() {
        val isLoggedIn = authRepository.isLoggedIn()
        android.util.Log.d("AuthViewModel", "checkLoginStatus - isLoggedIn: $isLoggedIn")
        
        _uiState.value = _uiState.value.copy(isLoggedIn = isLoggedIn)
        android.util.Log.d("AuthViewModel", "UI State updated in checkLoginStatus - isLoggedIn: ${_uiState.value.isLoggedIn}")
        
        if (isLoggedIn) {
            val userName = authRepository.getCurrentUserName()
            val userEmail = authRepository.getCurrentUserEmail()
            android.util.Log.d("AuthViewModel", "User already logged in - name: $userName, email: $userEmail")
            
            _uiState.value = _uiState.value.copy(
                currentUserName = userName,
                currentUserEmail = userEmail
            )
        }
        
        android.util.Log.d("AuthViewModel", "Final UI state - isLoggedIn: ${_uiState.value.isLoggedIn}")
    }
    
    fun forceNavigationUpdate() {
        android.util.Log.d("AuthViewModel", "forceNavigationUpdate called")
        // Force a state change to trigger navigation
        val currentState = _uiState.value
        _uiState.value = currentState.copy(isLoggedIn = false)
        _uiState.value = currentState.copy(isLoggedIn = true)
        android.util.Log.d("AuthViewModel", "Navigation update forced")
    }
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            android.util.Log.d("AuthViewModel", "=== LOGIN STARTED ===")
            android.util.Log.d("AuthViewModel", "Email: $email")
            
            _loginState.value = LoginState.Loading
            android.util.Log.d("AuthViewModel", "Login state set to Loading")
            
            authRepository.login(email, password)
                .catch { e ->
                    android.util.Log.e("AuthViewModel", "Login repository error", e)
                    _loginState.value = LoginState.Error(e.message ?: "Login failed")
                }
                .collect { result ->
                    android.util.Log.d("AuthViewModel", "Login result received")
                    result.fold(
                        onSuccess = { authResponse ->
                            android.util.Log.d("AuthViewModel", "=== LOGIN SUCCESS ===")
                            android.util.Log.d("AuthViewModel", "Login success: ${authResponse.user.email}")
                            _loginState.value = LoginState.Success(authResponse)
                            
                            android.util.Log.d("AuthViewModel", "Updating UI state - before: isLoggedIn=${_uiState.value.isLoggedIn}")
                            
                            // Force immediate state update
                            val newState = _uiState.value.copy(
                                isLoggedIn = true,
                                currentUserName = authResponse.user.name,
                                currentUserEmail = authResponse.user.email
                            )
                            _uiState.value = newState
                            
                                android.util.Log.d("AuthViewModel", "=== UI STATE UPDATED ===")
                                android.util.Log.d("AuthViewModel", "UI State updated after login - isLoggedIn: ${_uiState.value.isLoggedIn}")
                                android.util.Log.d("AuthViewModel", "User info: ${_uiState.value.currentUserName} (${_uiState.value.currentUserEmail})")
                            
                            // Double-check that the repository also reports logged in
                            val repoCheck = authRepository.isLoggedIn()
                            android.util.Log.d("AuthViewModel", "Repository isLoggedIn check: $repoCheck")
                        },
                        onFailure = { error ->
                            android.util.Log.e("AuthViewModel", "=== LOGIN FAILURE ===")
                            android.util.Log.e("AuthViewModel", "Login failure", error)
                            _loginState.value = LoginState.Error(error.message ?: "Login failed")
                        }
                    )
                }
        }
    }
    
    fun register(
        email: String,
        password: String,
        name: String,
        age: Int,
        height: Double,
        currentWeight: Double,
        targetWeight: Double,
        goal: String,
        activityLevel: String
    ) {
        viewModelScope.launch {
            try {
                _registerState.value = RegisterState.Loading
                
                authRepository.register(
                    email, password, name, age, height, 
                    currentWeight, targetWeight, goal, activityLevel
                )
                    .catch { e ->
                        android.util.Log.e("AuthViewModel", "Register error", e)
                        _registerState.value = RegisterState.Error(e.message ?: "Registration failed")
                    }
                    .collect { result ->
                        result.fold(
                            onSuccess = { authResponse ->
                                android.util.Log.d("AuthViewModel", "Register success: ${authResponse.user.email}")
                                _registerState.value = RegisterState.Success(authResponse)
                                
                                android.util.Log.d("AuthViewModel", "Updating UI state - before: isLoggedIn=${_uiState.value.isLoggedIn}")
                                
                                // Force immediate state update
                                val newState = _uiState.value.copy(
                                    isLoggedIn = true,
                                    currentUserName = authResponse.user.name,
                                    currentUserEmail = authResponse.user.email
                                )
                                _uiState.value = newState
                                
                                android.util.Log.d("AuthViewModel", "UI State updated after register - isLoggedIn: ${_uiState.value.isLoggedIn}")
                                android.util.Log.d("AuthViewModel", "User info: ${_uiState.value.currentUserName} (${_uiState.value.currentUserEmail})")
                                
                                // Double-check that the repository also reports logged in
                                val repoCheck = authRepository.isLoggedIn()
                                android.util.Log.d("AuthViewModel", "Repository isLoggedIn check: $repoCheck")
                            },
                            onFailure = { error ->
                                android.util.Log.e("AuthViewModel", "Register failure", error)
                                _registerState.value = RegisterState.Error(error.message ?: "Registration failed")
                            }
                        )
                    }
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Register exception", e)
                _registerState.value = RegisterState.Error(e.message ?: "Registration failed")
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                android.util.Log.d("AuthViewModel", "Starting logout process")
                
                authRepository.logout()
                    .collect { result ->
                        result.fold(
                            onSuccess = {
                                android.util.Log.d("AuthViewModel", "Logout successful")
                                _uiState.value = AuthUiState()
                                _loginState.value = LoginState.Idle
                                _registerState.value = RegisterState.Idle
                            },
                            onFailure = { error ->
                                android.util.Log.e("AuthViewModel", "Logout failed", error)
                                // Even if logout fails, clear local state
                                _uiState.value = AuthUiState()
                                _loginState.value = LoginState.Idle
                                _registerState.value = RegisterState.Idle
                            }
                        )
                    }
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Logout exception", e)
                // Clear local state even on exception
                _uiState.value = AuthUiState()
                _loginState.value = LoginState.Idle
                _registerState.value = RegisterState.Idle
            }
        }
    }
    
    fun clearLoginState() {
        _loginState.value = LoginState.Idle
    }
    
    fun clearRegisterState() {
        _registerState.value = RegisterState.Idle
    }
    
    fun refreshLoginStatus() {
        android.util.Log.d("AuthViewModel", "Manual refresh of login status requested")
        checkLoginStatus()
    }
}

data class AuthUiState(
    val isLoggedIn: Boolean = false,
    val currentUserName: String? = null,
    val currentUserEmail: String? = null
)

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val authResponse: AuthResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val authResponse: AuthResponse) : RegisterState()
    data class Error(val message: String) : RegisterState()
}