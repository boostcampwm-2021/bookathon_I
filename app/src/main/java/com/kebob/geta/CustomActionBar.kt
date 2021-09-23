package com.kebob.geta

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBar

class CustomActionBar(private val activity: Activity, private val actionBar: ActionBar) {

    @SuppressLint("InflateParams")
    fun setActionBar() {
        val customView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar, null)
        actionBar.customView = customView
    }
}
