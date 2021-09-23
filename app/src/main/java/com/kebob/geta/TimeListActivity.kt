package com.kebob.geta

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ActivityTimeListBinding

class TimeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setActionBar()

        val array = arrayOf(
            Meal("아침", "9:00", "11:00", "엄마", ""),
            Meal("점심", "9:00", "11:00", "엄마", ""),
            Meal("저녁", "9:00", "11:00", "엄마", "")
        )
        val adapter = TimeListAdapter(array)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val fabAdd = binding.fabAdd
        fabAdd.setOnClickListener {
            TODO("startActivity 등록 폼")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_delete_time -> TODO("delete item")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_time_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setActionBar() {
        supportActionBar?.let {
            CustomActionBar(this, it).setActionBar()
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "급식 시간 관리"
        }
    }
}
