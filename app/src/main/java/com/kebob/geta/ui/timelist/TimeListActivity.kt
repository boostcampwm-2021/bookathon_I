package com.kebob.geta.ui.timelist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.R
import com.kebob.geta.Util
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ActivityTimeListBinding
import com.kebob.geta.ui.CustomActionBar
import com.kebob.geta.ui.RegisterActivity

class TimeListActivity : AppCompatActivity(), DeleteTimeDialogFragment.DeleteTimeDialogListener {
    private lateinit var binding: ActivityTimeListBinding
    private lateinit var adapter: TimeListAdapter
    private lateinit var menu: Menu

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setActionBar()

        val list = listOf<Meal>()
        adapter = TimeListAdapter(list)
        adapter.setOnMyItemLongClickListener(object : TimeListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                val delete: MenuItem = menu.findItem(R.id.menu_delete_time)
                delete.isVisible = true

                for (i in 0 until adapter.itemCount) {
                    val item = binding.recyclerView.getChildAt(i)
                    val checkBox: CheckBox = item.findViewById(R.id.checkBox)
                    checkBox.visibility = View.VISIBLE

                    if (position == i) {
                        checkBox.isChecked = true
                    }
                }
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val fabAdd = binding.fabAdd
        fabAdd.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.menu_delete_time -> {
                showConfirmDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_time_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu ?: return false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        readRegisteredTime()
    }

    private fun setActionBar() {
        supportActionBar?.let {
            CustomActionBar(this, it).setActionBar()
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "급식 시간 관리"
        }
    }

    private fun showConfirmDialog() {
        val dialog = DeleteTimeDialogFragment()
        dialog.show(supportFragmentManager, "DeleteTimeDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        for (i in 0 until adapter.itemCount) {
            val view = binding.recyclerView.getChildAt(i)
            val checkBox: CheckBox = view.findViewById(R.id.checkBox)
            if (checkBox.isChecked) {
                deleteRegisteredTime(adapter.dataSet[i].mealName)
            }
            checkBox.visibility = View.GONE
        }
        readRegisteredTime()
        menu.findItem(R.id.menu_delete_time).isVisible = false
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        for (i in 0 until adapter.itemCount) {
            val view = binding.recyclerView.getChildAt(i)
            val checkBox: CheckBox = view.findViewById(R.id.checkBox)
            checkBox.visibility = View.GONE
        }

        menu.findItem(R.id.menu_delete_time).isVisible = false
    }

    private fun readRegisteredTime() {
        Util.parseMeal(database) {
            adapter.updateDataSet(it.toList())
        }
    }

    private fun deleteRegisteredTime(meal: String) {
        try {
            database.reference.child("meals").child(meal).removeValue()
            onResume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
