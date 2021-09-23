package com.kebob.geta.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kebob.geta.databinding.ActivityUserRegisterBinding

class UserRegisterActivity : AppCompatActivity() {
    private var _binding: ActivityUserRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}