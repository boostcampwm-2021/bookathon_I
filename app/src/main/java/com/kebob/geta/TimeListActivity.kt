package com.kebob.geta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.kebob.geta.databinding.ActivityTimeListBinding

class TimeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "급식 시간 추가"
        }

        val fabAdd = binding.fabAdd
        fabAdd.setOnClickListener {
            TODO("startActivity 등록 폼")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
