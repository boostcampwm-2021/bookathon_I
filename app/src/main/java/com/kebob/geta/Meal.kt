package com.kebob.geta

import android.graphics.Bitmap

data class Meal(
    val mealName: String,
    val mealType: String,
    val startTime: String,
    val endTime: String,
    var person: String?,
    var time: String?,
    val profile: Bitmap?
)
