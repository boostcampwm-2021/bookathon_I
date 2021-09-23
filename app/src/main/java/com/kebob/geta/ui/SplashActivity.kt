package com.kebob.geta.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.Util
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private var mealList: MutableList<Meal> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startLoading()
    }

    private fun startLoading() {
        Util.parseMeal(database) {
            mealList = it
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("meals", mealList as ArrayList)
            startActivity(intent)
            finish()
        }
    }
}