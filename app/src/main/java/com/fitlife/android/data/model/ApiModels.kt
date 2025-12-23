package com.fitlife.android.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

// Authentication Models
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val age: Int,
    val height: Double,
    val currentWeight: Double,
    val targetWeight: Double,
    val goal: String, // PERDER_PESO, MANTENER, GANAR_MUSCULO
    val activityLevel: String // SEDENTARIO, LIGERO, MODERADO, ACTIVO, MUY_ACTIVO
)

data class RefreshTokenRequest(
    val refreshToken: String
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long,
    val user: User
)

// User Model
data class User(
    val id: Long,
    val email: String,
    val name: String,
    val age: Int,
    val height: Double,
    val currentWeight: Double,
    val targetWeight: Double,
    val goal: String,
    val activityLevel: String,
    val profileImageUrl: String? = null,
    val isActive: Boolean = true,
    val emailVerified: Boolean = false,
    val streakDays: Int = 0,
    val totalPoints: Int = 0,
    val totalWeightLost: Double = 0.0,
    val createdAt: String,
    val lastLogin: String? = null,
    val allergies: List<String> = emptyList(),
    val dietaryRestrictions: List<String> = emptyList()
)

// Food Models
data class Food(
    val id: Long,
    val name: String,
    val brand: String? = null,
    val barcode: String? = null,
    val caloriesPer100g: Double,
    val proteinPer100g: Double,
    val carbsPer100g: Double,
    val fatPer100g: Double,
    val fiberPer100g: Double,
    val servingSize: String? = null,
    val imageUrl: String? = null,
    val isHealthy: Boolean = false,
    val glycemicIndex: Double? = null,
    val categories: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val vitamins: Map<String, Double> = emptyMap(),
    val minerals: Map<String, Double> = emptyMap()
)

data class FoodSearchResponse(
    val content: List<Food>,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int
)

data class FoodLogRequest(
    val foodId: Long,
    val quantity: Double,
    val unit: String,
    val mealType: String, // BREAKFAST, LUNCH, DINNER, SNACK
    val date: String
)

data class FoodLog(
    val id: Long,
    val food: Food,
    val quantity: Double,
    val unit: String,
    val mealType: String,
    val date: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)

data class DailyNutrition(
    val date: String,
    val totalCalories: Double,
    val totalProtein: Double,
    val totalCarbs: Double,
    val totalFat: Double,
    val totalFiber: Double,
    val calorieGoal: Double,
    val proteinGoal: Double,
    val carbsGoal: Double,
    val fatGoal: Double,
    val meals: Map<String, List<FoodLog>>
)

// Exercise Models
data class Exercise(
    val id: Long,
    val name: String,
    val category: String,
    val muscleGroups: List<String>,
    val equipment: String? = null,
    val instructions: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val difficulty: String, // BEGINNER, INTERMEDIATE, ADVANCED
    val caloriesPerMinute: Double
)

data class ExerciseLogRequest(
    val exerciseId: Long,
    val duration: Int, // minutes
    val sets: Int? = null,
    val reps: Int? = null,
    val weight: Double? = null,
    val date: String,
    val notes: String? = null
)

data class ExerciseLog(
    val id: Long,
    val exercise: Exercise,
    val duration: Int,
    val sets: Int? = null,
    val reps: Int? = null,
    val weight: Double? = null,
    val date: String,
    val caloriesBurned: Double,
    val notes: String? = null
)

// Social Models
data class Post(
    val id: Long,
    val user: User,
    val content: String,
    val imageUrl: String? = null,
    val type: String, // WORKOUT, MEAL, ACHIEVEMENT, GENERAL
    val likes: Int = 0,
    val comments: Int = 0,
    val isLiked: Boolean = false,
    val createdAt: String,
    val tags: List<String> = emptyList()
)

data class CreatePostRequest(
    val content: String,
    val imageUrl: String? = null,
    val type: String,
    val tags: List<String> = emptyList()
)

// Friends Models
data class FriendRequest(
    val id: Long,
    val sender: User,
    val receiver: User,
    val status: String, // PENDING, ACCEPTED, REJECTED
    val createdAt: String
)

data class FriendRequestRequest(
    val receiverEmail: String
)

// API Response wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val error: String? = null
)