package com.dht.notes.code.toast

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.dht.notes.R

/**
 * created by dht on 2021/12/13 09:43
 */
class CustomRewardAdToast(val context: Context) {

    private var toast: Toast? = null

    private var isCancel = false

    private val runnable = Runnable { showToast() }

    private val handler = Handler(Looper.getMainLooper())

    private fun getToast(): Toast {
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.BOTTOM, 10, 10)
        toast.view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null)
        return toast
    }

    fun showToast() {
        if (isCancel) {
            return
        }
        if (toast != null) {
            toast?.cancel()
            toast = null
        }
        toast = getToast()
        toast?.show()
        handler.postDelayed(runnable, 2800)
    }

    fun cancelToast() {
        isCancel = true
        handler.removeCallbacks(runnable)
        toast?.cancel()
    }
}