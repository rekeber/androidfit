package com.fitlife.android.di

import android.content.Context
import com.fitlife.android.data.api.ApiClient
import com.fitlife.android.data.api.ApiService
import com.fitlife.android.data.preferences.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
    
    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        ApiClient.initialize(context)
        return ApiClient.apiService
    }
}