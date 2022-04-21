package com.example.doska.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.example.doska.MainActivity
import com.example.doska.R
import com.example.doska.accounthelper.AccountHelper
import com.example.doska.databinding.SignDialogBinding

class DialogHelper(val act: MainActivity) {
    val accHelper = AccountHelper(act)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(act)
        val bindingDialog = SignDialogBinding.inflate(act.layoutInflater)
        val view = bindingDialog.root
        builder.setView(view)
        setDialogState(index, bindingDialog)
        val dialog = builder.create()
        bindingDialog.btSignUpIn.setOnClickListener {
            setOnClickSignUpIn(index, bindingDialog, dialog)
        }

        bindingDialog.btForgetP.setOnClickListener {
            setOnClickResedPassword(bindingDialog, dialog)
        }

        bindingDialog.btGoogleSignIn.setOnClickListener {
            accHelper.signInWithGoogle()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setOnClickResedPassword(bindingDialog: SignDialogBinding, dialog: AlertDialog?) {
        if (bindingDialog.edSignEmail.text.isNotEmpty()) {
            act.mAuth.sendPasswordResetEmail(bindingDialog.edSignEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            act,
                            R.string.email_reset_password_was_sent,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            dialog?.dismiss()
        } else {
            bindingDialog.tvDialogMessage.visibility = View.VISIBLE
        }

    }

    private fun setOnClickSignUpIn(
        index: Int,
        bindingDialog: SignDialogBinding,
        dialog: AlertDialog?
    ) {
        dialog?.dismiss()
        if (index == DialogConst.SIGN_UP_STATE) {
            accHelper.signUpWithEmail(
                bindingDialog.edSignEmail.text.toString(),
                bindingDialog.edSignPassword.text.toString()
            )
        } else {
            accHelper.signInWithEmail(
                bindingDialog.edSignEmail.text.toString(),
                bindingDialog.edSignPassword.text.toString()
            )
        }
    }

    private fun setDialogState(index: Int, bindingDialog: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            bindingDialog.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
            bindingDialog.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else {
            bindingDialog.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            bindingDialog.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
            bindingDialog.btForgetP.visibility = View.VISIBLE
        }
    }
}