package com.kebob.geta

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

object Util {
    fun parseMeal(database: FirebaseDatabase, onSuccessListener: (MutableList<Meal>)->Unit){
        val ref = database.reference
        var meals = mutableListOf<DataSnapshot>()
        var r = mutableListOf<Meal>()
        ref.child("meals").get().addOnSuccessListener { m ->
            m.children.forEach {
                meals.add(it)
            }
            meals.forEach{ meal ->
                val title = meal.key.toString()
                val content = meal.children
                var person = ""
                var time = ""
                var mealType = ""
                var startTime = ""
                var endTime = ""
                content.forEachIndexed{ i , it ->
                    if (i == 0) endTime = it.value.toString()
                    else if (i == 1) mealType = it.value.toString()
                    else if(i == 2) person = it.value.toString()
                    else if(i == 3) startTime = it.value.toString()
                    else if(i == 4) time = it.value.toString()
                }
                r.add(Meal(title, mealType, startTime, endTime,person,time, null))
            }
            onSuccessListener.invoke(r)
        }
    }
}