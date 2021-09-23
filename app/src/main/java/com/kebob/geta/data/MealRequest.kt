package com.kebob.geta.data

data class MealRequest(
    val mealType: String,
    val startTime: String,
    val endTime: String,
    val person: String?,
    val time: String?
)
