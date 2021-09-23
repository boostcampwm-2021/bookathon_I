package com.kebob.geta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.database.ktx.database
import com.kebob.geta.databinding.ActivityMainBinding
import com.kebob.geta.timelist.TimeListActivity

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mealListAdapter: MealListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var mealList : MutableList<Meal> = mutableListOf()
    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.parseMeal(database){
            mealListAdapter.updateList(it)
            mealList = it
        }
        setAdapter()
        setSupportActionBar(binding.tbMain)
        setActionBar()

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
//                updateData(position)
                mealListAdapter.updateList(mealList)
            }
        })
    }

//    private fun updateData(position: Int) {
//        when (mealList[position].type) {
//            Meal.CHECKED -> mealList[position].type = Meal.UNCHECKED
//            Meal.UNCHECKED -> mealList[position].type = Meal.CHECKED
//        }
//    }

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
