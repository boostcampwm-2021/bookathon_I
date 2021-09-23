package com.kebob.geta

import android.graphics.Bitmap

data class Meal(val type: Int, val title: String, val person: String, val time: String, val profile: Bitmap?) {
    companion object {
        const val CHECKED = 0
        const val UNCHECKED = 1
    }
}