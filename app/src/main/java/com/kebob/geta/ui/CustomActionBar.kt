package com.kebob.geta.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBar
import com.kebob.geta.R

class CustomActionBar(private val activity: Activity, private val actionBar: ActionBar) {

    @SuppressLint("InflateParams")
    fun setActionBar() {
        val customView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar, null)
        actionBar.customView = customView
    }
}
