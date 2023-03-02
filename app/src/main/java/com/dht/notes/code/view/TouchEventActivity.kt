package com.dht.notes.code.view

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.dht.notes.MainActivity
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_touch_event.*

/**
 *  created by Administrator on 2020/9/8 17:20
 */

class TouchEventActivity : Activity() {

    private val TAG = "TouchEventActivity"

    private val myHandler = MyHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
        frameA?.setOnClickListener {
            println("frameA = ${frameA}")

//            println("frameA = ${frameA}"
        }
        frameB?.setOnClickListener { println("frameb = ${frameA}") }
        frameC?.setOnClickListener { println("framec = ${frameA}") }


//        for (i in 1..1000) {
//            val thread = Thread(kotlinx.coroutines.Runnable {
//
//                Thread.sleep(100 * i.toLong())
//                myHandler.sendEmptyMessage(0)
//                println("TAG111 = ${TAG}")
//            })
//            thread.start()
//        }


    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: LeakCanary")
        
    }
    private class MyHandler : Handler() {
        private val TAG = "TouchEventActivity"

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            println("TAG = ${TAG}")
        }
    }
}