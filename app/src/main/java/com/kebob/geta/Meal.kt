package com.kebob.geta

import android.graphics.Bitmap
import java.io.Serializable

data class Meal(
    val mealName: String,
    val mealType: String,
    val startTime: String,
    val endTime: String,
    val person: String?,
    val time: String?,
    val profile: Bitmap?
): Serializable
