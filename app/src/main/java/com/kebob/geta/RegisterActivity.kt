package com.kebob.geta

import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ActivityRegisterBinding
import java.lang.Exception
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
        initListener()
    }

    private fun initListener() {
        with(binding) {
            imgClose.setOnClickListener {
                Log.d(TAG, "imgClose Clicked")
                finish()
            }

            btnRegister.setOnClickListener {
                if (this@RegisterActivity::mealType.isInitialized &&
                    this@RegisterActivity::mealName.isInitialized &&
                    !tvStartTimePicker.text.equals("시간을 선택해주세요") &&
                    !tvEndTimePicker.text.equals("시간을 선택해주세요")
                )
                    writeNewMeal(mealName)
                else {

                    Toast.makeText(applicationContext, "필수 항목을 선택해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            rgType.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: RadioGroup?, id: Int) {
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
            })

            etMealName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    mealName = p0.toString()
                    Log.d(TAG, "mealName: $mealName")
                }
            })

            etMealName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    tv: TextView?,
                    actionId: Int,
                    keyEvent: KeyEvent?
                ): Boolean {
                    if (keyEvent != null) {
                        if (((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                            || (actionId == EditorInfo.IME_ACTION_DONE)
                        ) {
                            val imm =
                                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(etMealName.getWindowToken(), 0)
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

    fun writeNewMeal(mealName: String) {
        val meal = Meal(
            mealType.name,
            binding.tvStartTimePicker.text.toString(),
            binding.tvEndTimePicker.text.toString(),
            "",
            ""
        )
        try {
            database.child(mealName).setValue(meal)
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