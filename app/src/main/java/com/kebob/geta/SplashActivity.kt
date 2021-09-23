package com.kebob.geta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.databinding.ActivityMainBinding
import com.kebob.geta.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var mBinding: ActivitySplashBinding? = null
    private val binding get() = mBinding!!

    private val database = Firebase.database
    private var mealList : MutableList<Meal> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startLoading()
    }
    private fun startLoading(){
        Util.parseMeal(database){
            mealList = it
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("meals", mealList as ArrayList)
            startActivity(intent)
            finish()
        }
    }
}