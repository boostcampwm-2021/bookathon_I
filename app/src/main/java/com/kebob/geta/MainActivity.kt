package com.kebob.geta

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.data.MealData
import com.kebob.geta.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mealListAdapter: MealListAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var mealList: MutableList<Meal> = mutableListOf()
    private val database = Firebase.database
    private val ref = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.parseMeal(database) {
            mealListAdapter.updateList(it)
            mealList = it
        }
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
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClick(view: View, position: Int) {
                updateData(position)
                Util.parseMeal(database) {
                    mealListAdapter.updateList(it)
                    mealList = it
                }
            }
        })
    }

    private fun updateData(position: Int) {
        when (mealList[position].person) {
            "" -> {
                mealList[position].apply {
                    person = "형님"
                    time = Util.getCurrentTime()
                    Log.d("time", time.toString())
                }
            }
            else -> {
                mealList[position].apply {
                    person = ""
                    time = ""
                }
            }
        }
        writeNewMeal(mealList[position])
    }

    private fun writeNewMeal(newMeal: Meal) {
        val mealData = MealData(
            newMeal.mealType,
            newMeal.startTime,
            newMeal.endTime,
            newMeal.person,
            newMeal.time
        )
        try {
            ref.child("meals").child(newMeal.mealName).setValue(mealData)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
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
