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
        _uiState.value = _uiState.value.copy(isLoggedIn = isLoggedIn)
        
        if (isLoggedIn) {
            _uiState.value = _uiState.value.copy(
                currentUserName = authRepository.getCurrentUserName(),
                currentUserEmail = authRepository.getCurrentUserEmail()
            )
        }
    }
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            authRepository.login(email, password)
                .catch { e ->
                    _loginState.value = LoginState.Error(e.message ?: "Login failed")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { authResponse ->
                            _loginState.value = LoginState.Success(authResponse)
                            _uiState.value = _uiState.value.copy(
                                isLoggedIn = true,
                                currentUserName = authResponse.user.name,
                                currentUserEmail = authResponse.user.email
                            )
                        },
                        onFailure = { error ->
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
            _registerState.value = RegisterState.Loading
            
            authRepository.register(
                email, password, name, age, height, 
                currentWeight, targetWeight, goal, activityLevel
            )
                .catch { e ->
                    _registerState.value = RegisterState.Error(e.message ?: "Registration failed")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { authResponse ->
                            _registerState.value = RegisterState.Success(authResponse)
                            _uiState.value = _uiState.value.copy(
                                isLoggedIn = true,
                                currentUserName = authResponse.user.name,
                                currentUserEmail = authResponse.user.email
                            )
                        },
                        onFailure = { error ->
                            _registerState.value = RegisterState.Error(error.message ?: "Registration failed")
                        }
                    )
                }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            _uiState.value = AuthUiState()
                            _loginState.value = LoginState.Idle
                            _registerState.value = RegisterState.Idle
                        },
                        onFailure = { error ->
                            // Even if logout fails, clear local state
                            _uiState.value = AuthUiState()
                            _loginState.value = LoginState.Idle
                            _registerState.value = RegisterState.Idle
                        }
                    )
                }
        }
    }
    
    fun clearLoginState() {
        _loginState.value = LoginState.Idle
    }
    
    fun clearRegisterState() {
        _registerState.value = RegisterState.Idle
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