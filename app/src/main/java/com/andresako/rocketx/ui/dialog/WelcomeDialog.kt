package com.andresako.rocketx.ui.dialog

import android.app.Dialog
import android.content.Context
import com.andresako.rocketx.R
import kotlinx.android.synthetic.main.dialog_welcome.*

class WelcomeDialog(context: Context) : Dialog(context) {
    init{
        setContentView(R.layout.dialog_welcome)
        dialogBtn.setOnClickListener{dismiss()}
    }
}