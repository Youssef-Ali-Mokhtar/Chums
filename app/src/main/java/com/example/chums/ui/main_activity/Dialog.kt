package com.example.chums.ui.main_activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import java.lang.ClassCastException

@Suppress("DEPRECATION")
class Dialog() : AppCompatDialogFragment() {
    lateinit var onDialogClickListner : OnDialogClickListner
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        builder.setTitle("Attention!")
            .setMessage("Do you really want to delete all the notes?")
            .setNegativeButton("No",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
            .setPositiveButton("Yes",object  : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                        onDialogClickListner.onDialogClick()
                }
            })
        return builder.create()
    }

    interface OnDialogClickListner{
        fun onDialogClick()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            onDialogClickListner = context as OnDialogClickListner
        }catch (e : ClassCastException){
            throw ClassCastException(context.toString() + "Must implement the dialog")
        }
    }
}