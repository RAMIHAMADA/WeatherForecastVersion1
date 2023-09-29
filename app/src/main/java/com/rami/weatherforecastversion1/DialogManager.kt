package com.rami.weatherforecastversion1

import android.app.AlertDialog
import android.content.Context

object DialogManager{
    fun locationSettingsDialog(context: Context, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable location?")
        dialog.setMessage("Location disabled, do you wont enable location?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){_,_->
            listener.onClick()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL"){_,_->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }
}