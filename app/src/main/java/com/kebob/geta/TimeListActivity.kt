package com.kebob.geta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kebob.geta.databinding.ActivityTimeListBinding

class TimeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fabAdd = binding.fabAdd
        fabAdd.setOnClickListener {
            TODO("startActivity 등록 폼")
        }
    }
}
