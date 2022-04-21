package com.example.doska.dialoghelper

import android.app.Activity
import android.app.AlertDialog
import com.example.doska.databinding.ProgressDialogLayoutBinding
import com.example.doska.databinding.SignDialogBinding

object ProgressDialog {
    fun  createProgressDialog(act: Activity): AlertDialog {
        val builder = AlertDialog.Builder(act)
        val bindingDialog = ProgressDialogLayoutBinding.inflate(act.layoutInflater)
        val view = bindingDialog.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}