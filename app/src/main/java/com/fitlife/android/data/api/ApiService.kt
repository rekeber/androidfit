package com.fitlife.android.data.api

import com.fitlife.android.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication endpoints
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
    
    // User endpoints
    @GET("users/profile")
    suspend fun getUserProfile(): Response<User>
    
    @PUT("users/profile")
    suspend fun updateUserProfile(@Body user: User): Response<User>
    
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Long): Response<User>
    
    // Food endpoints
    @GET("foods/search")
    suspend fun searchFoods(
        @Query("query") query: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<FoodSearchResponse>
    
    @GET("foods/{id}")
    suspend fun getFoodById(@Path("id") foodId: Long): Response<Food>
    
    // Nutrition endpoints
    @GET("nutrition/daily")
    suspend fun getDailyNutrition(@Query("date") date: String): Response<DailyNutrition>
    
    @POST("nutrition/log")
    suspend fun logFood(@Body request: FoodLogRequest): Response<FoodLog>
    
    // Exercise endpoints
    @GET("exercises")
    suspend fun getExercises(): Response<List<Exercise>>
    
    @POST("exercises/log")
    suspend fun logExercise(@Body request: ExerciseLogRequest): Response<ExerciseLog>
    
    // Social endpoints
    @GET("social/feed")
    suspend fun getFeed(@Query("page") page: Int = 0): Response<List<Post>>
    
    @POST("social/posts")
    suspend fun createPost(@Body request: CreatePostRequest): Response<Post>
    
    @POST("social/posts/{id}/like")
    suspend fun likePost(@Path("id") postId: Long): Response<Unit>
    
    // Friends endpoints
    @GET("friends")
    suspend fun getFriends(): Response<List<User>>
    
    @POST("friends/request")
    suspend fun sendFriendRequest(@Body request: FriendRequestRequest): Response<Unit>
    
    @GET("friends/requests")
    suspend fun getFriendRequests(): Response<List<FriendRequest>>
    
    @PUT("friends/requests/{id}/accept")
    suspend fun acceptFriendRequest(@Path("id") requestId: Long): Response<Unit>
}