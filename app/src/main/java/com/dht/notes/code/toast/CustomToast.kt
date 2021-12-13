package com.dht.notes.code.toast

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.dht.notes.R

/**
 * 功能描述:自定义toast显示时长
 */
class CustomToast(val context: Context,val viewGroup: ViewGroup?) {
    private var mToast: Toast? = null
    private var timeCount: TimeCount? = null
    private val gravity: Int = Gravity.BOTTOM
    private val mHandler: Handler = Handler()
    private var canceled = true


    private fun getToast(): Toast {
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.BOTTOM, 10, 10)
        toast.view = LayoutInflater.from(context).inflate(R.layout.custom_toast, viewGroup)
        return toast
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration
     */
    fun show(duration: Int) {
        timeCount = TimeCount(duration.toLong(), 1000)
        if (canceled) {
            timeCount?.start()
            canceled = false
            showUntilCancel()
        }
    }

    /**
     * 隐藏toast
     */
    fun hide() {
        if (mToast != null) {
            mToast?.cancel()
        }
        if (timeCount != null) {
            timeCount?.cancel()
        }
        canceled = true
    }

    private fun showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return
        }
        mToast = getToast()
        mToast?.setGravity(gravity, 0, 0)
        mToast?.show()
        mHandler.postDelayed(Runnable { showUntilCancel() }, 3500)



    }

    /**
     * 自定义计时器
     */
    private inner class TimeCount(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            hide()
        }
    }

}