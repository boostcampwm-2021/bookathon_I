package com.kebob.geta.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.kebob.geta.R
import com.kebob.geta.ui.timelist.DeleteTimeDialogFragment

class CheckMealDialogFragment : DialogFragment() {
    private lateinit var listener: CheckMealDialogFragment.CheckMealDialogListener

    interface CheckMealDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_check_meal)
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
