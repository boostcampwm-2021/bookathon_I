package com.kebob.geta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kebob.geta.databinding.ActivityUserRegisterBinding

class UserRegisterActivity : AppCompatActivity() {
    private var mBinding: ActivityUserRegisterBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }
}