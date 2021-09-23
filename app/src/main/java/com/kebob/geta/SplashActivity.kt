package com.kebob.geta

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.databinding.ActivityMainBinding
import com.kebob.geta.databinding.ActivitySplashBinding
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {
    private var mBinding: ActivitySplashBinding? = null
    private val binding get() = mBinding!!

    private val database = Firebase.database
    private var mealList: MutableList<Meal> = mutableListOf()


    private lateinit var sharedPreferences: SharedPreferences
    private var prefUser by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        sharedPreferences = getSharedPreferences(UserRegisterActivity.login, MODE_PRIVATE)
//        prefUser = sharedPreferences.getBoolean(UserRegisterActivity.u, true)
        startLoading()
    }

    private fun startLoading() {
        Util.parseMeal(database) {
            mealList = it
//            if (prefUser) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("meals", mealList as ArrayList)
            startActivity(intent)
            finish()
//            }
//            else{
//                val intent = Intent(this,UserRegisterActivity::class.java)
//                intent.putExtra("meals", mealList as ArrayList)
//                startActivity(intent)
//                finish()
//            }
        }
    }
}