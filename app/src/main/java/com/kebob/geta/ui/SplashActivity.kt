package com.kebob.geta.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.Util
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ActivitySplashBinding
import com.kebob.geta.ui.main.MainActivity
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private var mealList: MutableList<Meal> = mutableListOf()

    private lateinit var sharedPreferences: SharedPreferences
    private var login by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(UserRegisterActivity.login, MODE_PRIVATE)
        login = sharedPreferences.getBoolean(UserRegisterActivity.u,false)
        startLoading()
    }

    private fun startLoading() {
        Util.parseMeal(database) {

            mealList = it
            if (login){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("meals", mealList as ArrayList)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, UserRegisterActivity::class.java)
                intent.putExtra("meals", mealList as ArrayList)
                startActivity(intent)
                finish()
            }
        }
    }
}
