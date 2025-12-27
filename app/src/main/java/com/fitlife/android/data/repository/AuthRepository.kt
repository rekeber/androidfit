package com.fitlife.android.data.repository

import com.fitlife.android.data.api.ApiService
import com.fitlife.android.data.model.*
import com.fitlife.android.data.preferences.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    
    suspend fun login(email: String, password: String): Flow<Result<AuthResponse>> = flow {
        try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            
            if (response.isSuccessful) {
                val authResponse = response.body()!!
                
                // Save tokens and user info
                tokenManager.saveTokens(
                    authResponse.accessToken,
                    authResponse.refreshToken,
                    authResponse.expiresIn
                )
                
                tokenManager.saveUserInfo(
                    authResponse.user.id,
                    authResponse.user.email,
                    authResponse.user.name
                )
                
                emit(Result.success(authResponse))
            } else {
                emit(Result.failure(Exception("Login failed: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun register(
        email: String,
        password: String,
        name: String,
        age: Int,
        height: Double,
        currentWeight: Double,
        targetWeight: Double,
        goal: String,
        activityLevel: String
    ): Flow<Result<AuthResponse>> = flow {
        try {
            val request = RegisterRequest(
                email = email,
                password = password,
                name = name,
                age = age,
                height = height,
                currentWeight = currentWeight,
                targetWeight = targetWeight,
                goal = goal,
                activityLevel = activityLevel
            )
            
            android.util.Log.d("AuthRepository", "Sending register request for: $email")
            val response = apiService.register(request)
            
            if (response.isSuccessful) {
                val authResponse = response.body()
                if (authResponse != null) {
                    android.util.Log.d("AuthRepository", "Register response received, saving tokens")
                    
                    // Save tokens and user info
                    tokenManager.saveTokens(
                        authResponse.accessToken,
                        authResponse.refreshToken,
                        authResponse.expiresIn
                    )
                    
                    tokenManager.saveUserInfo(
                        authResponse.user.id,
                        authResponse.user.email,
                        authResponse.user.name
                    )
                    
                    android.util.Log.d("AuthRepository", "Tokens saved successfully")
                    emit(Result.success(authResponse))
                } else {
                    android.util.Log.e("AuthRepository", "Register response body is null")
                    emit(Result.failure(Exception("Registration failed: Empty response")))
                }
            } else {
                val errorMsg = "Registration failed: ${response.code()} ${response.message()}"
                android.util.Log.e("AuthRepository", errorMsg)
                emit(Result.failure(Exception(errorMsg)))
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Register exception", e)
            emit(Result.failure(e))
        }
    }
    
    suspend fun logout(): Flow<Result<Unit>> = flow {
        try {
            android.util.Log.d("AuthRepository", "Starting logout process")
            
            val response = apiService.logout()
            android.util.Log.d("AuthRepository", "Logout API call completed, clearing tokens")
            
            tokenManager.clearTokens()
            android.util.Log.d("AuthRepository", "Tokens cleared successfully")
            
            if (response.isSuccessful) {
                android.util.Log.d("AuthRepository", "Logout successful")
                emit(Result.success(Unit))
            } else {
                android.util.Log.w("AuthRepository", "Logout API failed but tokens cleared: ${response.code()}")
                emit(Result.success(Unit)) // Still success since tokens are cleared
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Logout exception, clearing tokens anyway", e)
            // Clear tokens even if API call fails
            tokenManager.clearTokens()
            emit(Result.success(Unit))
        }
    }
    
    fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }
    
    fun getCurrentUserId(): Long {
        return tokenManager.getUserId()
    }
    
    fun getCurrentUserEmail(): String? {
        return tokenManager.getUserEmail()
    }
    
    fun getCurrentUserName(): String? {
        return tokenManager.getUserName()
    }
    
    fun refreshLoginStatus(): Boolean {
        val isLoggedIn = tokenManager.isLoggedIn()
        android.util.Log.d("AuthRepository", "refreshLoginStatus - isLoggedIn: $isLoggedIn")
        return isLoggedIn
    }
}