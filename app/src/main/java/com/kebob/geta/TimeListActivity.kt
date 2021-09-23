package com.kebob.geta

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kebob.geta.databinding.ActivityTimeListBinding

class TimeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setActionBar()

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

    private fun setActionBar() {
        supportActionBar?.let {
            CustomActionBar(this, it).setActionBar()
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "급식 시간 추가"
        }
    }
}
