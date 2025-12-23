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
            
            val response = apiService.register(request)
            
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
                emit(Result.failure(Exception("Registration failed: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun logout(): Flow<Result<Unit>> = flow {
        try {
            val response = apiService.logout()
            tokenManager.clearTokens()
            
            if (response.isSuccessful) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception("Logout failed: ${response.message()}")))
            }
        } catch (e: Exception) {
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
}