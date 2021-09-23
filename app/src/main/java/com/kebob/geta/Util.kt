package com.kebob.geta

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import org.threeten.bp.LocalDateTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object Util {
    fun parseMeal(database: FirebaseDatabase, onSuccessListener: (MutableList<Meal>) -> Unit) {
        val ref = database.reference
        val meals = mutableListOf<DataSnapshot>()
        val mealList = mutableListOf<Meal>()
        ref.child("meals").get().addOnSuccessListener { m ->
            m.children.forEach {
                meals.add(it)
            }
            meals.forEach { meal ->
                val title = meal.key.toString()
                val content = meal.children
                var person = ""
                var time = ""
                var mealType = ""
                var startTime = ""
                var endTime = ""
                content.forEachIndexed { i, it ->
                    if (i == 0) endTime = it.value.toString()
                    else if (i == 1) mealType = it.value.toString()
                    else if (i == 2) person = it.value.toString()
                    else if (i == 3) startTime = it.value.toString()
                    else if (i == 4) time = it.value.toString()
                }
                mealList.add(Meal(title, mealType, startTime, endTime, person, time, null))
            }
            mealList.sortBy { it.startTime }
            onSuccessListener.invoke(mealList)
        }
    }

    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }
}
