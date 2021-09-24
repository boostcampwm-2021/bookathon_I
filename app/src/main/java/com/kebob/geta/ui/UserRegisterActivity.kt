package com.kebob.geta.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.kebob.geta.R
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ActivityUserRegisterBinding
import com.kebob.geta.ui.main.MainActivity
import java.lang.Exception

class UserRegisterActivity : AppCompatActivity() {
    private val TAG = "UserRegisterActivity"
    private var mBinding: ActivityUserRegisterBinding? = null
    private val binding get() = mBinding!!

    var userName = ""
    var userUrl = ""

    private val database = Firebase.database.reference

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferenceEditor: SharedPreferences.Editor

    private lateinit var mealList : MutableList<Meal>

    private var isChecking = true
    private var checkedId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        mealList = intent.getSerializableExtra("meals") as MutableList<Meal>
        sharedPreferences = getSharedPreferences(login, MODE_PRIVATE)
        preferenceEditor = sharedPreferences.edit()
    }

    private fun initListener() {
        with(binding) {
            rgParent.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: RadioGroup?, id: Int) {
                    if (id != -1 && isChecking){
                        isChecking = false
                        rgChild.clearCheck()
                        checkedId = id
                    }
                    isChecking = true
                    setUrl(checkedId)
                }
            })
            rgChild.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: RadioGroup?, id: Int) {
                    if (id != -1 && isChecking){
                        isChecking = false
                        rgParent.clearCheck()
                        checkedId = id
                    }
                    isChecking = true
                    setUrl(checkedId)
                }
            })

            etUser.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    userName = p0.toString()
                }
            })

            etUser.setOnEditorActionListener(object : TextView.OnEditorActionListener {
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
                            imm.hideSoftInputFromWindow(etUser.getWindowToken(), 0)
                            return true
                        }
                    }
                    return false
                }
            })

            btnRegisterUser.setOnClickListener {
                if (userName != "" && userUrl != ""){
                    try {
                        database.child("users").child(userName).setValue(userUrl)
                        preferenceEditor.putBoolean(u, true)
                        preferenceEditor.putString(userN, userName)
                        preferenceEditor.apply()
                        val intent = Intent(this@UserRegisterActivity, MainActivity::class.java)
                        intent.putExtra("meals", mealList as ArrayList)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    }
                }
                else
                    Toast.makeText(applicationContext, "필수 항목 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUrl(id: Int){
        when(id) {
            R.id.rb_dad -> {
                userUrl =
                    "https://user-images.githubusercontent.com/62787596/134565130-35203932-93e7-4285-8d9e-faf0742ca394.png"
            }
            R.id.rb_mom -> {
                userUrl =
                    "https://user-images.githubusercontent.com/62787596/134565134-3706a519-4c05-443e-bc4b-69ac74b911fe.png"
            }
            R.id.rb_son -> {
                userUrl =
                    "https://user-images.githubusercontent.com/62787596/134565138-889d240c-6c9c-4e74-aadf-7bf12411a912.png"
            }
            R.id.rb_daughter -> {
                userUrl =
                    "https://user-images.githubusercontent.com/62787596/134565145-53eccba2-28de-4242-907e-4064a310c4d3.png"
            }
        }
    }

    companion object {
        const val login = "login"
        const val u = "user"
        const val userN = "userName"
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
}