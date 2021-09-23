package com.kebob.geta.ui

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.R
import com.kebob.geta.data.MealRequest
import com.kebob.geta.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val TAG = "RegisterActivity"

    private lateinit var mealType: MealType
    private lateinit var mealName: String

    private val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbRegister)
        setActionBar()
        initListener()
    }

    private fun initListener() {
        with(binding) {
            lottieRegister.setAnimation("gooddog.json")
            lottieRegister.loop(true)
            lottieRegister.scale = 0.5f
            lottieRegister.playAnimation()

            btnRegister.setOnClickListener {
                if (this@RegisterActivity::mealType.isInitialized &&
                    this@RegisterActivity::mealName.isInitialized &&
                    !mealName.equals("") &&
                    !tvStartTimePicker.text.equals(getString(R.string.tv_select_time)) &&
                    !tvEndTimePicker.text.equals(getString(R.string.tv_select_time))
                )
                    writeNewMeal(mealName)
                else {
                    Toast.makeText(applicationContext, "필수 항목을 선택해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            rgType.setOnCheckedChangeListener { p0, id ->
                when (id) {
                    R.id.rb_meal -> {
                        mealType = MealType.Meal
                    }
                    R.id.rb_snack -> {
                        mealType = MealType.Snack
                    }
                }
                Log.d(TAG, "mealType : ${mealType.name}")
            }

            etMealName.addTextChangedListener {
                mealName = it.toString()
                Log.d(TAG, "mealName: $mealName")
            }

            etMealName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    tv: TextView?,
                    actionId: Int,
                    keyEvent: KeyEvent?
                ): Boolean {
                    if (keyEvent != null) {
                        if (((keyEvent.keyCode == KeyEvent.KEYCODE_ENTER))
                            || (actionId == EditorInfo.IME_ACTION_DONE)
                        ) {
                            val imm =
                                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(etMealName.windowToken, 0)
                            return true
                        }
                    }
                    return false
                }
            })

            tvStartTimePicker.setOnClickListener {
                setTime(it as TextView)
            }

            tvEndTimePicker.setOnClickListener {
                setTime(it as TextView)
            }
        }
    }

    private fun setActionBar() {
        supportActionBar?.let {
            CustomActionBar(this, it).setActionBar()
            it.setHomeAsUpIndicator(R.drawable.ic_close)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "등록"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setTime(view: TextView) {
        val currentTime = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            currentTime.set(Calendar.HOUR_OF_DAY, hour)
            currentTime.set(Calendar.MINUTE, minute)
            view.text = SimpleDateFormat("HH:mm").format(currentTime.time)
        }
        val timePickerDialog = TimePickerDialog(
            this,
            timeSetListener,
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.setTitle("시간을 정해주세요.")
        timePickerDialog.show()
    }

    private fun writeNewMeal(mealName: String) {
        val meal = MealRequest(
            mealType.name,
            binding.tvStartTimePicker.text.toString(),
            binding.tvEndTimePicker.text.toString(),
            "",
            ""
        )
        try {
            database.child("meals").child(mealName).setValue(meal)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null)
            view = View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyBoard()
        return super.dispatchTouchEvent(ev)
    }

    enum class MealType {
        Meal,
        Snack
    }
}
