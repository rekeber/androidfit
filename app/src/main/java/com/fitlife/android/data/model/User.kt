package com.fitlife.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val email: String,
    val name: String,
    val profileImage: String? = null,
    val age: Int,
    val height: Double,
    val currentWeight: Double,
    val targetWeight: Double,
    val activityLevel: ActivityLevel,
    val goal: FitnessGoal,
    val dietaryRestrictions: List<String> = emptyList(),
    val allergies: List<String> = emptyList(),
    val createdAt: String,
    val lastLogin: String? = null,
    val streakDays: Int = 0,
    val totalWeightLost: Double = 0.0,
    val friendIds: List<String> = emptyList(),
    val preferences: Map<String, String> = emptyMap()
) : Parcelable

enum class ActivityLevel {
    SEDENTARIO,
    LIGERO,
    MODERADO,
    ACTIVO,
    MUY_ACTIVO
}

enum class FitnessGoal {
    PERDER_PESO,
    MANTENER,
    GANAR_MUSCULO
}

@Parcelize
data class UserStats(
    val bmi: Double,
    val dailyCalories: Double,
    val weeklyWorkouts: Int,
    val weeklyCaloriesBurned: Double,
    val currentStreak: Int
) : Parcelable