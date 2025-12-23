package com.fitlife.android.data.api

import android.content.Context
import com.fitlife.android.data.preferences.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/" // Para emulador Android
    // Para dispositivo físico usar: "http://192.168.1.XXX:8080/api/v1/"
    
    private var retrofit: Retrofit? = null
    private var tokenManager: TokenManager? = null
    
    fun initialize(context: Context) {
        tokenManager = TokenManager(context)
    }
    
    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            
            val authInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()
                val token = tokenManager?.getAccessToken()
                
                val newRequest = if (token != null) {
                    originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    originalRequest
                }
                
                val response = chain.proceed(newRequest)
                
                // Handle token refresh if needed
                if (response.code == 401 && token != null) {
                    response.close()
                    
                    // Try to refresh token
                    val refreshToken = tokenManager?.getRefreshToken()
                    if (refreshToken != null) {
                        // TODO: Implement token refresh logic
                        // For now, just clear tokens and redirect to login
                        tokenManager?.clearTokens()
                    }
                }
                
                response
            }
            
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        
        return retrofit!!
    }
    
    val apiService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }
}