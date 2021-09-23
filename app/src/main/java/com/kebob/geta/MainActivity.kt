package com.kebob.geta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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
        setSupportActionBar(binding.tbMain)
        setActionBar()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meal_list, menu)
        return true
    }

    private fun setActionBar() {
        supportActionBar?.let {
            CustomActionBar(this, it).setActionBar()
            it.title = "개밥타임"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_tb_settings -> {
                val intent = Intent(this, TimeListActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
