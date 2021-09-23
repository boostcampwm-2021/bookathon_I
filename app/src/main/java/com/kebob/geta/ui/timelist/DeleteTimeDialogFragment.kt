package com.kebob.geta.ui.timelist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.kebob.geta.R

class DeleteTimeDialogFragment : DialogFragment() {
    private lateinit var listener: DeleteTimeDialogListener

    interface DeleteTimeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_delete_time)
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
            listener = context as DeleteTimeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement DeleteTimeDialogListener"))
        }
    }
}
