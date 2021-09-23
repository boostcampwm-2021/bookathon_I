package com.kebob.geta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kebob.geta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mealListAdapter: MealListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var mealList = mutableListOf(
        Meal(Meal.CHECKED, "아침", "엄마", "Mon 13:00", null)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        mLayoutManager = LinearLayoutManager(this)
        binding.rvMealList.layoutManager = mLayoutManager
    }

    private fun setAdapter() {
        mealListAdapter = MealListAdapter()
        binding.rvMealList.adapter = mealListAdapter
        mealListAdapter.updateList(mealList)
        mealListAdapter.setOnItemClickListener(object : MealListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                // Firebase data 수정하기

                mealListAdapter.updateList(mealList)
            }
        })
    }
}