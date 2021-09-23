package com.kebob.geta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kebob.geta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mealListAdapter: MealListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var mealList = mutableListOf(
        Meal(Meal.CHECKED, "아침", "엄마", "Mon 13:00", null),
        Meal(Meal.UNCHECKED, "저녁", "아들1", "Mon 18:00", null)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        mLayoutManager = LinearLayoutManager(this)
        binding.rvMealList.layoutManager = mLayoutManager

        Firebase.messaging.subscribeToTopic("all")
            .addOnCompleteListener { task ->
                Toast.makeText(baseContext, "환영합니다.", Toast.LENGTH_SHORT).show()
            }

    }

    private fun setAdapter() {
        mealListAdapter = MealListAdapter()
        binding.rvMealList.adapter = mealListAdapter
        mealListAdapter.updateList(mealList)
        mealListAdapter.setOnItemClickListener(object : MealListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                // Firebase data 수정하기
                updateData(position)
                mealListAdapter.updateList(mealList)
            }
        })
    }

    private fun updateData(position: Int) {
        when (mealList[position].type) {
            Meal.CHECKED -> mealList[position].type = Meal.UNCHECKED
            Meal.UNCHECKED -> mealList[position].type = Meal.CHECKED
        }
    }
}
