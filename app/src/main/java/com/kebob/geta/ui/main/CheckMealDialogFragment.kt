package com.kebob.geta.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.kebob.geta.R
import com.kebob.geta.data.Meal

class CheckMealDialogFragment(val meal: Meal) : DialogFragment() {
    private lateinit var listener: CheckMealDialogFragment.CheckMealDialogListener

    interface CheckMealDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var message = when (meal.mealType) {
                "Meal" -> getString(R.string.common_meal_surve)
                "Snack" -> getString(R.string.common_snack_surve)
                "Medicine" -> getString(R.string.common_medicine_surve)
                else -> getString(R.string.meal_complete)
            }
            if (meal.person?.isNotBlank() == true) {
                message = getString(R.string.meal_cancle)
            }

            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                .setPositiveButton(R.string.yes) { dialog, id ->
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(R.string.no) { dialog, id ->
                    listener.onDialogNegativeClick(this)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as CheckMealDialogFragment.CheckMealDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement CheckMealDialogListener"))
        }
    }
}
