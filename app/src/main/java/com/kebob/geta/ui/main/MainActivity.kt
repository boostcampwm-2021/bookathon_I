package com.kebob.geta.ui.main

import android.animation.Animator
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kebob.geta.R
import com.kebob.geta.Util
import com.kebob.geta.data.Meal
import com.kebob.geta.data.MealRequest
import com.kebob.geta.databinding.ActivityMainBinding
import com.kebob.geta.ui.CustomActionBar
import com.kebob.geta.ui.UserRegisterActivity
import com.kebob.geta.ui.timelist.TimeListActivity


class MainActivity : AppCompatActivity(), CheckMealDialogFragment.CheckMealDialogListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mealListAdapter: MealListAdapter = MealListAdapter()

    private var mealList: MutableList<Meal> = mutableListOf()

    private val database = Firebase.database
    private val databaseReference = database.reference

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName: String

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbMain)
        setActionBar()
        setAdapter()
        setUserMap()

        binding.lottieHome.setAnimation("paricle.json")
        binding.lottieHome.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                binding.lottieHome.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        sharedPreferences = getSharedPreferences(UserRegisterActivity.login, MODE_PRIVATE)
        userName = sharedPreferences.getString(UserRegisterActivity.userN, "default").toString()

        mealList = intent.getSerializableExtra("meals") as MutableList<Meal>

        Firebase.messaging.subscribeToTopic("all")
            .addOnCompleteListener { task ->
                Toast.makeText(baseContext, "오늘도 개밥타임", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        updateMealList()
    }

    private fun setAdapter() {
        binding.rvMealList.adapter = mealListAdapter
        binding.rvMealList.layoutManager = LinearLayoutManager(this)
        mealListAdapter.updateList(mealList)
        mealListAdapter.setOnItemClickListener(object : MealListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (position < mealList.size - 1 && mealList[position + 1].person != "") return
                if (position > 0 && mealList[position - 1].person == "") return
                showConfirmDialog(position)
            }
        })
    }

    private fun updateMealList() {
        Util.parseMeal(database) {
            mealListAdapter.updateList(it)
            mealList = it
            showEmptyResult(it.isEmpty())
        }
    }

    private fun setUserMap() {
        Util.getUserMap(database) {
            mealListAdapter.updateUserMap(it)
        }
    }

    private fun showEmptyResult(isEmpty: Boolean) {
        binding.layoutNoAlarm.visibility = View.GONE
        if (isEmpty) {
            binding.layoutNoAlarm.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.drawable.ic_waiting_dog)
                .into(binding.imgNoData)
        }
    }

    private fun updateData(position: Int) {
        when (mealList[position].person) {
            "" -> {
                binding.lottieHome.visibility = View.VISIBLE
                binding.lottieHome.playAnimation()

                mealList[position].apply {
                    person = userName
                    time = Util.getCurrentTime()
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
        val mealData = MealRequest(
            newMeal.mealType,
            newMeal.startTime,
            newMeal.endTime,
            newMeal.person,
            newMeal.time
        )
        try {
            databaseReference.child("meals")
                .child(newMeal.mealName)
                .setValue(mealData)
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

    private fun showConfirmDialog(pos: Int) {
        position = pos
        mealList[pos]?.let {
            val dialog = CheckMealDialogFragment(it)
            dialog.show(supportFragmentManager, "CheckMealDialogFragment")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        updateData(position)
        updateMealList()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }
}
