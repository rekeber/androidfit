package com.fitlife.android.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "fitlife_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val TOKEN_EXPIRY_KEY = "token_expiry"
        private const val USER_ID_KEY = "user_id"
        private const val USER_EMAIL_KEY = "user_email"
        private const val USER_NAME_KEY = "user_name"
    }
    
    fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Long) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .putLong(TOKEN_EXPIRY_KEY, expiryTime)
            .apply()
    }
    
    fun getAccessToken(): String? {
        val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        val expiryTime = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
        
        return if (token != null && System.currentTimeMillis() < expiryTime) {
            token
        } else {
            null
        }
    }
    
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }
    
    fun isTokenValid(): Boolean {
        val expiryTime = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
        return System.currentTimeMillis() < expiryTime
    }
    
    fun saveUserInfo(userId: Long, email: String, name: String) {
        sharedPreferences.edit()
            .putLong(USER_ID_KEY, userId)
            .putString(USER_EMAIL_KEY, email)
            .putString(USER_NAME_KEY, name)
            .apply()
    }
    
    fun getUserId(): Long {
        return sharedPreferences.getLong(USER_ID_KEY, -1)
    }
    
    fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL_KEY, null)
    }
    
    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }
    
    fun clearTokens() {
        sharedPreferences.edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .remove(TOKEN_EXPIRY_KEY)
            .remove(USER_ID_KEY)
            .remove(USER_EMAIL_KEY)
            .remove(USER_NAME_KEY)
            .apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getAccessToken() != null && isTokenValid()
    }
}